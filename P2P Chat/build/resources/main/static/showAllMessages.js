
// DOM loading
document.addEventListener("DOMContentLoaded", () => {

    // =====================================
    // ===== Show all Messages ==========
    // =====================================

    // Fetch data from backend
    fetch("http://localhost:8080/api/message/receive", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => response.text())
        .then(data => {
            const message = JSON.parse(data);

            message.forEach(message => {
                const userName = message.userName;
                const text = message.text;

                // Create the elements for the message
                const listUl = document.querySelector(".list ul")
                const li = document.createElement("li");
                const userNameParagraph = document.createElement("p");
                userNameParagraph.textContent = userName;

                const textParagraph = document.createElement("p");
                text.textContent = text;

                li.appendChild(userNameParagraph);
                li.appendChild(text);

                listUl.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Error:", error);
        });
});