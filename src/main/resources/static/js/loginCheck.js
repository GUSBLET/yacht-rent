document.addEventListener("DOMContentLoaded", function () {
        const form = document.querySelector("form");
        const emailInput = document.querySelector("#login_email");
        const passwordInput = document.querySelector("#login_password");
        const emailError = document.querySelector("#emailError");
        const passwordError = document.querySelector("#passwordError");

        form.addEventListener("submit", function (event) {
            let isValid = true;

            if (!emailInput.value.trim()) {
                isValid = false;
                emailError.textContent = "Email cannot be empty.";
                event.preventDefault();
            } else if (!isValidEmail(emailInput.value)) {
                isValid = false;
                emailError.textContent = "Invalid email format.";
                event.preventDefault();
            } else {
                emailError.textContent = "";
            }

            if (!passwordInput.value.trim()) {
                isValid = false;
                passwordError.textContent = "Password cannot be empty.";
                event.preventDefault();
            } else if (passwordInput.value.length < 5) {
                isValid = false;
                passwordError.textContent = "Password must be at least 5 characters long.";
                event.preventDefault();
            } else {
                passwordError.textContent = "";
            }

            if (!isValid) {
                event.preventDefault();
            }
        });

        function isValidEmail(email) {
            const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
            return emailPattern.test(email);
        }
    });