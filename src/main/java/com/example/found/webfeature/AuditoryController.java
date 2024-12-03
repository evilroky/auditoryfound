package com.example.found.webfeature;

import com.example.found.entities.Building;
import com.example.found.entities.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@Controller
public class AuditoryController {
    @Autowired
    private AuditoryService auditoryService;

    @GetMapping("/")
    public String home(){
        List<Building> buildings = auditoryService.buildingCheck();
        if(buildings.isEmpty()){
            auditoryService.buildingCheck();
        }
        return "index";
    }

    @GetMapping("/search")
    public String searchAuditory(
            @RequestParam String buildingId,
            @RequestParam String roomNumber,
            Model model) {

        Integer buildingIdInt = Integer.parseInt(buildingId);
        Integer roomNumberInt = Integer.parseInt(roomNumber);

        try {
            Integer intex = Integer.parseInt(roomNumber);
            // Получаем информацию об аудитории
            Map<String, Object> result = auditoryService.getAuditoryInfo(buildingIdInt, roomNumberInt);

            if (result.isEmpty()) {
                model.addAttribute("error", "Нет данных об аудитории. Добавить?");
                return "index"; // Переход на index.html при отсутствии данных
            }

            model.addAttribute("auditoryDetails", result.get("auditoryDetails"));
            model.addAttribute("devicesList", result.get("devicesList"));
            model.addAttribute("buildingId", buildingIdInt);
            model.addAttribute("roomNumber", roomNumberInt);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при получении данных: " + e.getMessage());
            e.printStackTrace();
            return "index"; // Переход на index.html при возникновении ошибки
        }

        return "search"; // Переход на search.html при успешном поиске
    }

    @GetMapping("/edit")
    public String editAuditory(@RequestParam String buildingId,
                               @RequestParam String roomNumber,
                               Model model) {


        Integer buildingIdInt = Integer.parseInt(buildingId);
        Integer roomNumberInt = Integer.parseInt(roomNumber);

        List<Device> allDevices = auditoryService.getAllDevices();

        Map<String, Object> result = auditoryService.getAuditoryInfo(buildingIdInt, roomNumberInt);
        if (result.isEmpty()) {
            model.addAttribute("error", "Аудитория не найдена.");
            return "search";
        }
        model.addAttribute("auditoryDetails", result.get("auditoryDetails"));
        model.addAttribute("devicesList", result.get("devicesList"));
        model.addAttribute("buildingId", buildingIdInt); // Передаем buildingId в модель
        model.addAttribute("roomNumber", roomNumberInt);
        model.addAttribute("allDevices", allDevices);// Передаем roomNumber в модель
        return "edit";
    }

    @PostMapping("/edit")
    public String updateDeviceInfo(@RequestParam Integer buildingId,
                                   @RequestParam Integer roomNumber,
                                   @RequestParam Integer auditoriesId,
                                   @RequestParam List<Integer> deviceListId,
                                   @RequestParam List<Integer> deviceId,
                                   @RequestParam List<Boolean> availability,
                                   @RequestParam List<String> text) {

        auditoryService.updateDevices(buildingId, roomNumber, deviceListId, deviceId, availability, text, auditoriesId);

        return "redirect:/api/search?roomNumber=" + roomNumber + "&buildingId=" + buildingId;
    }

    @GetMapping("/create")
    public String createAuditory(Model model){
        List<Device> devices = auditoryService.getAllDevices(); // Получаем все устройства из базы
        model.addAttribute("devices", devices); // Передаем список устройств на страницу
        return "create";
    }

    @PostMapping("/create")
    public String createAuditory(@RequestParam String buildingId,
                                 @RequestParam String roomNumber,
                                 @RequestParam Integer floor,
                                 @RequestParam String floorImage,
                                 @RequestParam(required = false) List<String> devicesText,
                                 @RequestParam List<Boolean> devicesAvailability,
                                 @RequestParam List<Integer> deviceIds) {

        Integer buildingIdInt = Integer.parseInt(buildingId);
        Integer roomNumberInt = Integer.parseInt(roomNumber);

        auditoryService.createAuditory(buildingIdInt,roomNumberInt,floor,floorImage,deviceIds,devicesAvailability,devicesText);
        return "redirect:/api/search?roomNumber=" + roomNumber + "&buildingId=" + buildingId;
    }

    @GetMapping("/createdevice")
    public String createDeviceFromNew(){
        return "createdevice";
    }

    @PostMapping("/createdevice")
    public String createDeviceFromNew(@RequestParam String name){
        auditoryService.createDevice(name);
        return "index";
    }

    @GetMapping("/{buildingId}/{roomNumber}")
    public ResponseEntity<Map<String, Object>> getAuditoryDetails(
            @PathVariable Integer buildingId,
            @PathVariable Integer roomNumber) {

        Map<String, Object> result = auditoryService.getAuditoryInfo(buildingId, roomNumber);

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
