// // Room buttons will populate the search box when clicked
// document.querySelectorAll('.room-button').forEach(button => {
//     button.addEventListener('click', () => {
//         document.getElementById('search-box').value = button.innerText;
//     });
// });

// Search button functionality
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
