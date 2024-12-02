import getData from "./get_data.js"

console.log(getData("https://mdn.github.io/learning-area/javascript/oojs/json/superheroes.json"));


const name_audit = '302гд'

const audit = [
    ['Комопьютерный класс','Да', 'Необходимо обновить конфигурацию 1С.'], 
    ['Кондиционер', 'Да', ''],
    ['Проектор', 'Да', 'Необходимо взять пульт в 402гд, лаборатория'], 
    ['Камера', 'Нет', '']
]


document.querySelector('.number_audit').innerHTML = `<h3 class="aud">`+ name_audit + `</h3>`

document.querySelector('.content').innerHTML = `<table class="device"></table>`


for(let i=0; i < audit.length; i++) {

    let row = document.createElement('tr')
    row.innerHTML = `
        <td><b>${audit[i][0]}</b></td>
        <td>
            <select id="select` + i + `" class="toggle">
                <option value="y">Да</option>
                <option value="n">Нет</option>
            </select>
        </td>
    `
    document.querySelector('.device').appendChild(row) 

    let comment = document.createElement('textarea')
    comment.setAttribute("id", `${i}`)
    comment.innerHTML = `${audit[i][2]}`

    document.querySelector('.device').appendChild(comment)

}


// Handle "Подтвердить изменения" button click
document.querySelector(".confirm-button").addEventListener("click", () => {
    for(let i=0; i < audit.length; i++) {
        var select = document.getElementById(`select` + i)
        var comment = document.getElementById(i)
        
        audit[i][1] = select.options[select.selectedIndex].text
        audit[i][2] = comment.value
        // alert(select.options[select.selectedIndex].text)
    }
    console.log(audit)
    alert("Изменения подтверждены");
    //window.location.href = 'search.html';
});