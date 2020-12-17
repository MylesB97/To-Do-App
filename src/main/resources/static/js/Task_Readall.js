

function createElement(data) {

    for (const q of data) {

        const myDiv = document.querySelector('#myDiv');

        const br = document.createElement('br');
        const pid = document.createElement('p');
        const ptask = document.createElement('p');
        const button = document.createElement('a')
        const div = document.createElement('div');
        const newLine = document.createElement('br');

        console.log(q);
        if (q.tasks > 0 || q.tasks != undefined) {
            for (let key in q.tasks) {
                console.log(key)
            }
        }
    }
}


fetch('http://localhost:9092/task/read/')
    .then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }

            // Examine the text in the response
            response.json().then(function (commentData) {

                console.log(commentData)
                console.log(commentData[0].employee)

                createElement(commentData)


                let table = document.querySelector("table");
                let data = Object.keys(commentData[0]); // first record in the array pos 0

                createTableHead(table, data);
                createTableBody(table, commentData);

            });
        }
    )
    .catch(function (err) {
        console.log('Fetch Error :-S', err);
    });

function createTableHead(table, data) {
    let tableHead = table.createTHead();
    let row = tableHead.insertRow();
    console.log("data", data)
    for (let keys of data) {
        if(keys != 'listOfTasks'){
            let th = document.createElement("th");
        let text = document.createTextNode(keys);
        th.appendChild(text);
        row.appendChild(th);
        }
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
    for (let record of data) {
        let row = table.insertRow();
        for (let values in record) {
            if(values != 'listOfTasks'){
                let cell = row.insertCell();
                let text = document.createTextNode(record[values]);
                cell.appendChild(text);
            }
           
        }

        let newCell = row.insertCell();
        let myViewButton = document.createElement("a");
        let myButtonValue = document.createTextNode("View")
        myViewButton.className = "btn btn-warning";
        myViewButton.href = "tsk_update.html?id=" + record.id
        myViewButton.appendChild(myButtonValue);
        newCell.appendChild(myViewButton)
        let newCellDelete = row.insertCell();
        let myDelButton = document.createElement("button");
        let myButtonValue1 = document.createTextNode("Delete")
        myDelButton.className ="btn btn-danger";
        myDelButton.onclick = function(){
          delTask(record.id);
          //refresh();
          return false;
    };
        myDelButton.appendChild(myButtonValue1);
        newCellDelete.appendChild(myDelButton)
    }
}

document.querySelector("form.viewRecord3").addEventListener("submit", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord3").elements;
    console.log(formElements)
    let empID = formElements["commentEID"].value;
    let title = formElements["commentTitle"].value;
    let desc = formElements["commentDesc"].value;
    let finished = formElements["commentFinished"].checked;


    let data = {
        "title": title,
        "description": desc,
        "finished": finished,
        "employee": {
            "id":empID
        }
    }
    console.log(data)

    sendData(data)
    refresh()
});

function sendData(data) {
    fetch("http://localhost:9092/task/create", {
        method: 'POST',
        headers: {
            "Content-type": "application/json"
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