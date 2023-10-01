'use strict';


const sideBar = document.querySelector(".sidebar");
const sidebarBtn = document.querySelector(".info_more-btn");

sidebarBtn.addEventListener('click', function () {
    sideBar.classList.toggle('active');
  }
);


const orderItemsGet = document.querySelectorAll(".order-item");

const modalContainer = document.querySelector(".modal-container-order");
const overlay = document.querySelector('.overlay')

orderItemsGet.forEach(function(orderItem) {
  orderItem.addEventListener("click", function() {
    modalContainer.classList.add("active");
    overlay.classList.add('active');
  });
});

const closeButtonModal = document.querySelector(".order-close-btn");
closeButtonModal.addEventListener("click", function() {
  modalContainer.classList.remove("active")
  overlay.classList.remove('active');
});

// //navigation
//
// const navigationLinks = document.querySelectorAll(".nav-link");
// const about = document.querySelector('.about')
// const review = document.querySelector('.review')
//
// function HandlerNavigationLinks(event) {
//   navigationLinks.forEach(function (navigationLink
//   ) {
//     navigationLink.classList.remove('active')
//   })
//   if (event.target.id === 'review') {
//     review.classList.add('active')
//     about.classList.remove('active')
//
//   }
//   else {
//     review.classList.remove('active')
//     about.classList.add('active')
//   }
//   event.target.classList.add('active')
// }
//
// navigationLinks.forEach(function (navigationLink) {
//   navigationLink.addEventListener('click', HandlerNavigationLinks)
// })