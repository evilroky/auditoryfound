package com.example.found.webfeature;

import com.example.found.entities.*;
import com.example.found.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuditoryService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private AuditoryRepository auditoryRepository;
    @Autowired
    private DevicesListRepository devicesListRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    public Map<String, Object> getAuditoryInfo(Integer buildingId, Integer roomNumber) {
        Map<String, Object> response = new HashMap<>();

        // Находим этажи, связанные с buildingId
        List<Floor> floors = floorRepository.findAllByBuildingId(buildingId);
        //
        System.out.println(floors);
        //
        if (!floors.isEmpty()) {
            for (Floor floor : floors) {
                // Ищем аудиторию по номеру и ID этажа
                Optional<Auditory> auditoryOpt = auditoryRepository.findByNumberAndFloorsId(roomNumber, floor.getId());

                if (auditoryOpt.isPresent()) {
                    Auditory auditory = auditoryOpt.get();

                    // Собираем информацию о здании и этаже
                    Building building = buildingRepository.findById(Long.valueOf(floor.getBuildingId())).orElse(null);


                    if (building != null) {
                        Map<String, Object> auditoryDetails = new HashMap<>();
                        auditoryDetails.put("number", roomNumber);
                        auditoryDetails.put("buildingShortname", building.getShortname());

                        String floorImageName = floor.getImage();
                        String floorImagePath = "/img/" + floorImageName + ".jpg";
                        auditoryDetails.put("floorImage", floorImagePath);

                        auditoryDetails.put("floor", floor.getFloor());

                        response.put("auditoryDetails", auditoryDetails);

                        // Находим устройства, связанные с аудиторией
                        List<DevicesList> devicesList = devicesListRepository.findByAuditoriesId(auditory.getId());
                        List<Map<String, Object>> devicesListResponse = new ArrayList<>();


                        for (DevicesList deviceEntry : devicesList) {
                            Optional<Device> device = deviceRepository.findById(Long.valueOf(deviceEntry.getDeviceId()));
                            if (device.isPresent()) {
                                Map<String, Object> deviceInfo = new HashMap<>();
                                deviceInfo.put("id", deviceEntry.getId());
                                deviceInfo.put("name", device.get().getName());
                                deviceInfo.put("deviceId", deviceEntry.getDeviceId());
                                deviceInfo.put("availability", deviceEntry.getAvailability());
                                deviceInfo.put("text", deviceEntry.getText());
                                deviceInfo.put("auditoriesId", deviceEntry.getAuditoriesId());
                                devicesListResponse.add(deviceInfo);
                            }
                        }

                        response.put("devicesList", devicesListResponse);
                        return response; // Возвращаем информацию, если нашли аудиторию
                    }
                }
            }
        }

        // Если ничего не найдено, возвращаем пустую карту
        return response;
    }

    public List<Integer> getDeviceListIds(Integer buildingId, Integer roomNumber) {
        // Находим этажи, связанные с buildingId
        List<Floor> floors = floorRepository.findAllByBuildingId(buildingId);

        if (floors.isEmpty()) {
            return Collections.emptyList();
        }

        // Ищем аудиторию по номеру и этажам
        for (Floor floor : floors) {
            Optional<Auditory> auditoryOpt = auditoryRepository.findByNumberAndFloorsId(roomNumber, floor.getId());

            if (auditoryOpt.isPresent()) {
                Auditory auditory = auditoryOpt.get();

                // Находим устройства, связанные с найденной аудиторией
                List<DevicesList> devices = devicesListRepository.findByAuditoriesId(auditory.getId());

                // Собираем только IDs устройств
                return devices.stream()
                        .map(DevicesList::getId)
                        .collect(Collectors.toList());
            }
        }

        // Если аудитория не найдена
        return Collections.emptyList();
    }


    @Transactional
    public void updateDevices(Integer buildingId,
                              Integer roomNumber,
                              List<Integer> deviceListId,
                              List<Integer> deviceId,
                              List<Boolean> availability,
                              List<String> text,
                              Integer auditoriesId) {
        // Получаем текущие устройства для данной аудитории
        List<Integer> currentDeviceListIds = getDeviceListIds(buildingId, roomNumber);

        // Удаляем устройства, которые есть в currentDeviceListIds, но отсутствуют в deviceListId
        List<Integer> idsToDelete = currentDeviceListIds.stream()
                .filter(id -> !deviceListId.contains(id))
                .collect(Collectors.toList());

        // Удаляем эти устройства из базы данных
        idsToDelete.forEach(devicesListRepository::deleteById);

        // Обновляем существующие устройства, которые есть в обоих списках
        for (int i = 0; i < deviceListId.size(); i++) {
            Integer currentDeviceListId = deviceListId.get(i);
            Integer currentDeviceId = deviceId.get(i);
            Boolean currentAvailability = availability.get(i);
            String currentText = text.get(i);

            // Если устройство присутствует в обоих списках (т.е. оно существует в базе и должно быть обновлено)
            if (currentDeviceListId != null && currentDeviceListIds.contains(currentDeviceListId)) {
                devicesListRepository.updateDeviceInfo(currentDeviceListId, currentDeviceId, currentAvailability, currentText);
            }
        }

        // Добавляем новые устройства, которые есть в deviceListId, но отсутствуют в базе (т.е. id устройства == null)
        for (int i = 0; i < deviceListId.size(); i++) {
            Integer currentDeviceListId = deviceListId.get(i);

            // Если deviceListId == null, это значит новое устройство
            if (currentDeviceListId == null) {
                DevicesList newDevice = new DevicesList();
                newDevice.setAuditoriesId(auditoriesId);
                newDevice.setDeviceId(deviceId.get(i));
                newDevice.setAvailability(availability.get(i));
                newDevice.setText(text.get(i));
                devicesListRepository.save(newDevice);
            }
        }
    }




    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Transactional
    public void createAuditory(Integer buildingId, Integer roomNumber, Integer floor, String floorImage,
                                        List<Integer> deviceIds, List<Boolean> devicesAvailability, List<String> devicesText) {
        Floor floorEntity = new Floor();
        floorEntity.setBuildingId(buildingId);
        floorEntity.setFloor(floor);
        floorEntity.setImage(floorImage);
        floorRepository.save(floorEntity);

        Auditory auditory = new Auditory();
        auditory.setNumber(roomNumber);
        auditory.setFloorsId(floorEntity.getId());
        auditoryRepository.save(auditory);

        for (int i = 0; i < deviceIds.size(); i++) {
            DevicesList device = new DevicesList();
            device.setAuditoriesId(auditory.getId());
            device.setDeviceId(deviceIds.get(i));
            device.setAvailability(devicesAvailability.get(i));
            device.setText(devicesText != null && devicesText.size() > i ? devicesText.get(i) : null);
            devicesListRepository.save(device);
        }
    }

    @Transactional
    public void createDevice(String name){
        Device device = new Device();
        device.setName(name);
        deviceRepository.save(device);
    }
}