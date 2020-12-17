const params = new URLSearchParams(window.location.search);

for (let param of params) {
    console.log("here i am", param)
    let id = param[1];
    console.log(id);
    getData(id)
}

function getData(id) {
    fetch('http://localhost:9092/task/readByID/' + id)
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }
                // Examines the text in the response
                response.json().then(function (data) {
                    console.log("MY DATA OBJ", data)

                    // Inserts Data into Form
                    document.querySelector("input#commentID").value = data.id
                    document.querySelector("input#commentTitle").value = data.title
                    document.querySelector("input#commentDesc").value = data.description
                    document.querySelector("input#commentFinished").checked = data.finished
                    


                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}


document.querySelector("form.viewRecord").addEventListener("submit", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord").elements;
    console.log(formElements)
    let empID = formElements["commentEID"].value;
    let title = formElements["commentTitle"].value;
    let desc = formElements["commentDesc"].value;
    let finished = formElements["commentFinished"].checked;
    let id = formElements["commentID"].value;

    console.log(finished)
    let data = {
        "title": title,
        "description": desc,
        "finished": finished,
        "employee": {
            "id":empID
        }
    }

    console.log(data)

    sendData(data, id)
    refresh();
});


function sendData(data, id) {
    fetch("http://localhost:9092/task/update/" + id, {
        method: 'put',
        headers: {
            "Content-type": "application/json;"
        },
        body: JSON.stringify(data)
    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}

function refresh() {    
    setTimeout(function () {
        location.reload()
    }, 200);
}