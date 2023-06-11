
// DOM loading
document.addEventListener("DOMContentLoaded", () => {

    // =====================================
    // ========== Create new Post ==========
    // =====================================

    // Get the form element
    const newPostForm = document.querySelector(".form");

    // Add a submit event listener to the form
    newPostForm.addEventListener("submit", (event) => {
        event.preventDefault();

        // Get the entered title and URL values
        const url = newPostForm.querySelector('input[name="url"]').value;
        const alias = newPostForm.querySelector('input[name="alias"]').value;
        const message = document.querySelector(".message");
        message.textContent = "";

        // Fetch data to backend
        const formData = {
            url: url,
            alias: alias,
            hitCount: 0,
            secretCode: null
        };

        fetch("http://localhost:8080/api/links", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                // response.text();
                if (response.ok) {
                    return response.json()
                } else if (response.status === 400) {
                    throw new Error("Your alias is already in use!")
                }
            })
            .then(data => {
                console.log(data);
                console.log("You provided data fot Link creation");

                // Show the success message
                message.textContent = `Your URL is aliased to ${data.alias} and your secret code is ${data.secretCode}`;

                // Reset the form fields
                newPostForm.reset();
            })
            .catch(error => {
                console.error("Error:", error);

                // Show the error message
                const message = document.querySelector(".message");
                message.style.color = "red";
                message.textContent = "Your alias is already in use!";
            });
    });
});