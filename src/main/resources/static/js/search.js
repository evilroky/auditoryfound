document.querySelector(".refresh-button").addEventListener("click", function() {
    alert("Обновление состояния аудитории...");
    window.location.href = 'edit.html';
    // Refresh room information from server or database if needed
});

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