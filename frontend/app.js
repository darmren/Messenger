const API = 'http://localhost:8080';

function saveToken(token) {
    localStorage.setItem('token', token);
}

function getToken() {
    return localStorage.getItem('token');
}

function logout() {
    localStorage.removeItem('token');
    location.href = 'login.html';
}

async function login() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;
    const res = await fetch(`${API}/api/users/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
    });
    if (res.ok) {
        const data = await res.json();
        saveToken(data.token);
        location.href = 'index.html';
    } else {
        alert('Ошибка входа');
    }
}

async function register() {
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;
    const res = await fetch(`${API}/api/users/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
    });
    if (res.ok) {
        const data = await res.json();
        saveToken(data.token);
        location.href = 'index.html';
    } else {
        alert('Ошибка регистрации');
    }
}

async function searchUsers() {
    const q = document.getElementById('search').value;
    if (q.length < 2) return;
    const res = await fetch(`${API}/api/users/search?username=${encodeURIComponent(q)}`, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    });
    if (res.ok) {
        const users = await res.json();
        const results = document.getElementById('results');
        results.innerHTML = '';
        users.forEach(u => {
            const div = document.createElement('div');
            div.className = 'user';
            div.textContent = u.username;
            div.onclick = () => {
                location.href = `chat.html?user=${u.username}`;
            };
            results.appendChild(div);
        });
    }
}

function sendMessage() {
    const msg = document.getElementById('message').value;
    alert(`(заглушка) Отправлено: ${msg}`);
}
