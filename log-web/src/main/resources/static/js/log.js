let serverName = prompt("서버명 입력하세요");

document.querySelector("#serverName").innerHTML = serverName;

const eventSource = new EventSource(`http://localhost:9991/log/${serverName}`);

eventSource.onmessage = (event) => {
    const data = JSON.parse(event.data);
    initYourMessage(data);
}


function getReceiveMsgBox(data) {
    return `<div class="received_withd_msg">
    <p>${data.logline} </p>
  </div>`;
}


function initYourMessage(data) {
    let chatBox = document.querySelector("#log-box");
    let receivedBox = document.createElement("div");
    receivedBox.className = "incoming_msg";

    receivedBox.innerHTML = getReceiveMsgBox(data);
    chatBox.append(receivedBox);

    document.documentElement.scrollTop = document.body.scrollHeight;
}

