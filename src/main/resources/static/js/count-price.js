function countPrice(){
    var dateOfStartValue = document.getElementById("dateOfStart").value;
    var dateOfFinishValue = document.getElementById("dateOfFinish").value;
    var hourOfStartValue = document.getElementById("hourOfStart").value;
    var hourOfFinishValue = document.getElementById("hourOfFinish").value;
    var priceField = document.getElementById("price");

    var json = JSON.stringify({
        dateOfStart: dateOfStartValue,
        dateOfFinish: dateOfFinishValue,
        hourOfStart: hourOfStartValue,
        hourOfFinish: hourOfFinishValue
    });

    var url = "../order/count-price?dateOfStart=" + dateOfStartValue + "&dateOfFinish=" + dateOfFinishValue + "&hourOfStart="
        + hourOfStartValue + "&hourOfFinish=" + hourOfFinishValue;

    axios.get(url)
        .then(function (response){
            priceField.value = response.data
        })
        .catch(function (error) {
            // Handle errors here
            console.error("Error:", error);
        });
}