// // Handle "Подтвердить изменения" button click
// document.querySelector(".confirm-button").addEventListener("click", () => {
//     alert("Изменения подтверждены");
//     window.location.href = 'search.html';
//     // Additional code to submit changes can be added here
// });

document.getElementById('search-button').addEventListener('click', () => {
    const roomNumber = document.getElementById('search-box').value;
    const roomBuild = document.getElementById('build').value;
    if (roomNumber) {
        alert("Поиск информации для аудитории: " + roomNumber + roomBuild);
        window.location.href = 'search.html';
        // Code to perform search or navigate to a new page can be added here
    } else {
        alert("Пожалуйста, введите номер аудитории.");
    }
});