const modalContent = document.querySelector('.main-content-modal');
const modalTemp = document.getElementById("modal-temp");
const span = document.getElementsByClassName("close")[0];

span.onclick = function() {
    modalTemp.style.display = "none";
    const modalContent = document.querySelector('.main-content-modal');
    while (modalContent.firstChild) {
        modalContent.removeChild(modalContent.firstChild);
    }
}

// window.onclick = function(event) {
//     if (event.target === modalTemp) {
//         modalTemp.style.display = "none";
//         const modalContent = document.querySelector('.main-content-modal');
//         while (modalContent.firstChild) {
//             modalContent.removeChild(modalContent.firstChild);
//         }
//     }
// }


function getCallModalWindow(url){
    axios.get(url)
        .then(response => {
            openModal(response.data);
        }).catch(error => {
        console.error('Error:', error);
    });
}

function getCallModalWindowWithIdRequest(url, id){
    axios.get(url + '?id=' + id)
        .then(response => {
            openModal(response.data);
        }).catch(error => {
        console.error('Error:', error);
    });
}

function openModal(data){
    modalContent.innerHTML = data;

    modalTemp.style.display = 'block';
}