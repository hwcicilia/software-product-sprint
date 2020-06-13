// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
export function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greet');
  greetingContainer.innerText = greeting;
}

export function fetchSomething() {
  fetch('/data').then(response => response.json()).then((messages) => {
    let fetchElement = document.getElementById('fetchSomething');
    messages.forEach((message) => {
      fetchElement.appendChild(createMessageElement(message));
    })
  });
}


function createMessageElement(message) {
  const box = document.createElement('article');
  box.className = 'tile is-child box';

  const content = document.createElement('div');
  content.className = 'content';

  const paragraph = document.createElement('p');

  const name = document.createElement('strong');
  name.innerText = message.name;

  const email = document.createElement('small');
  email.innerText = message.email;

  const newLine = document.createElement('br');

  const space = document.createTextNode(" ");

  const messageText = document.createTextNode(message.message);

  paragraph.appendChild(name)
  paragraph.appendChild(space)
  paragraph.appendChild(email)
  paragraph.appendChild(newLine)
  paragraph.appendChild(messageText)

  content.appendChild(paragraph)
  box.appendChild(content)
  return box;
}
