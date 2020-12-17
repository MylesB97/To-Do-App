
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

                    //Creation of Table for Tasks
                    let table = document.querySelector("table");
                    let tasks = Object.keys(data.listOfTasks[0])

                    createTableHead(table, tasks);
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
    refresh()
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

function createTableHead(table, data) {
    let tableHead = table.createTHead();
    let row = tableHead.insertRow();
    console.log("List of Task: ", data)
    for (let keys of data) {
        let th = document.createElement("th");
        let text = document.createTextNode(keys);
        th.appendChild(text);
        row.appendChild(th);
    }
    let th2 = document.createElement("th")
    let text2 = document.createTextNode("View");
    th2.appendChild(text2);
    row.appendChild(th2);
    let th3 = document.createElement("th")
    let text3 = document.createTextNode("Delete");
    th3.appendChild(text3);
    row.appendChild(th3);
}

function createTableBody(table, data) {
    for(let records of data){
        let row = table.insertRow();
        for(let values in records){
            let cell = row.insertCell();
            let text = document.createTextNode(records[values]);
            cell.appendChild(text);
        }
        let newCell = row.insertCell();
        let myViewButton = document.createElement("a");
        let myButtonValue = document.createTextNode("View")
        myViewButton.className = "btn btn-warning";
        myViewButton.href = "tsk_update.html?id=" + records.id
        myViewButton.appendChild(myButtonValue);
        newCell.appendChild(myViewButton)
        let newCellDelete = row.insertCell();
        let myDelButton = document.createElement("button");
        let myButtonValue1 = document.createTextNode("Delete")
        myDelButton.className = "btn btn-danger";
        myDelButton.onclick = function () {
            delTask(records.id);
            refresh();
        }
        myDelButton.appendChild(myButtonValue1);
        newCellDelete.appendChild(myDelButton)
    }
}

function delTask(id){
    fetch("http://localhost:9092/task/delete/" + id, {
        method: 'DELETE'

    })
    .then(function(id){
        console.log('Request response succeeded: Record Deleted  of ID:' + id)
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
