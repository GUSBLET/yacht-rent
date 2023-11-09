function sendFormWithObject(path, model, callback){
    const config = {
        headers: {
            'Content-Type': 'multipart/form-data' // Установите правильный тип содержимого
        }
    };

    axios.post(path, model, config)
        .then(function (response) {
            console.log(response.data);
            callback(response.data);
        })
        .catch(function (error) {
            console.error(error);
            return null;
        });
}

function createNewYacht(){
    const form = document.getElementById("addingYachtForm");

    var yacht = new FormData(form);

    sendFormWithObject(form.action, yacht, function (response) {
        if (response !== null) {
            while (modalContent.firstChild) {
                modalContent.removeChild(modalContent.firstChild);
            }
            modalContent.innerHTML = response;
        }
    });
}
   /*   Массив файлов будет доступен по имени поля 'photos'
     const selectedFiles = creatingYachtDTO.getAll('photos');

      Вы можете перебрать все выбранные файлы
     for (let i = 0; i < selectedFiles.length; i++) {
         const file = selectedFiles[i];
         console.log(file.name); // Имя файла
          Здесь можно выполнить дополнительные действия с каждым файлом
     }*/
