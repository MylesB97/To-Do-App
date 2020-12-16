
// we set a constant to find the values from the search bar
const params = new URLSearchParams(window.location.search);

for (let param of params) {
    console.log("here i am", param)
    let id = param[1];
    console.log(id);
    getData(id)
}

function getData(id) {
    fetch('http://localhost:9092/employee/readByID/' + id)
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
                    document.querySelector("input#commentName").value = data.name
                    //  document.querySelector("input#commentTask").value = data.make

                    let table = document.querySelector("table");

                    createTableHead(table, data.listOfTasks);
                    createTableBody(table, data.listOfTasks);

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
    let id = formElements["commentID"].value;
    let name = formElements["commentName"].value;

    let data = {
        "name": name,
    }
    console.log("Updating Employee Number: " + id + " to Name: " + name)

    sendData(data, id)
    // postData(noteTitle,noteBody)
});

document.querySelector("form.viewRecord").addEventListener("delete", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord").elements;
    console.log(formElements)
    let id = formElements["commentID"].value;

    console.log("Deleting Employee Number: " + id)

    deleteByID(id)
    // postData(noteTitle,noteBody)
});


function sendData(data, id) {
    fetch("http://localhost:9092/employee/update/" + id, {
        method: 'put',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
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


function deleteByID(id) {
    fetch("http://localhost:9092/employee/delete/" + id, {
        method: 'delete',

    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}

function createTableHead(table, data) {
    let tableHead = table.createTHead();
    let row = tableHead.insertRow();
    console.log("data", data)
    for (let keys of data) {
        let th = document.createElement("th");
        let text = document.createTextNode(keys);
        th.appendChild(text);
        row.appendChild(th);
    }
}

function createTableBody(table, data) {
    for (let records of data) {
            let row = table.insertRow();
            for (let values in records) {
                let cell = row.insertCell();
                let text = document.createTextNode(records[values]);
                cell.appendChild(text);
            }
    }
}