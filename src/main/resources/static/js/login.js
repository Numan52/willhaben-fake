document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
    form.addEventListener("submit", function (event) {
        event.preventDefault()
        const formData = new FormData(form);

        fetch("/login", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorMessage => {
                        alert(`Error: ${errorMessage}`);
                    });
                } else {
                    // Extract username or user-related data from the response if available
                    return response.json().then(data => {
                        const username = data.username
                        localStorage.setItem("loggedInUser", JSON.stringify({username}))

                        // Dispatch a custom event after successful login
                        // const loginEvent = new CustomEvent("userLoggedIn", { detail: { username: data.username } });
                        // document.dispatchEvent(loginEvent);
                        // console.log(loginEvent)

                        // Redirect to the home page
                        window.location.href = "/";
                    });
                }
            })
            .catch(error => {
                alert(`Error: ${error.message}`);
            })
    });
});