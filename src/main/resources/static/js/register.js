document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("registrationForm");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent default form submission

        const formData = new FormData(form);

        fetch("/register", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorMsg => {
                        alert("Registration failed. " + errorMsg);
                    });
                } else {
                    window.location.href = "/login";
                }
            })
            .catch(error => alert(error.message));
    });
});