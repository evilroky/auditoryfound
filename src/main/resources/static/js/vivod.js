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
        <td><b>${audit[i][1]}</b></td>
    `
    document.querySelector('.device').appendChild(row) 

    let comment = document.createElement('textarea')
    comment.innerHTML = `${audit[i][2]}`

    document.querySelector('.device').appendChild(comment)

}

