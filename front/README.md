# MDD – Frontend (Angular)

This is the frontend of **MDD – Monde de Dév**, a minimal social network for developers.  
It allows users to subscribe to topics, read and write articles, comment, and manage their profile.

---

## 🚀 Features

- User authentication with JWT  
- Topic subscription and filtering  
- Article creation and display  
- Commenting  
- Profile management  
- Responsive layout based on Figma mockups

---

## 🛠️ Technologies

- Angular 14  
- Angular Material  
- TypeScript  
- SCSS  
- JWT (with `auth.interceptor.ts`)  
- REST API (Spring Boot backend)

---

## 📦 Requirements

- Node.js v16+  
- Angular CLI

Install Angular CLI if needed:

```bash
npm install -g @angular/cli
```

---

## 🧭 Clone the Repository

```bash
git clone https://github.com/natalliaSkiba/Developpez-une-application-full-stack-complete.git
cd Developpez-une-application-full-stack-complete/frontend
```

---

## ▶️ Run the App

```bash
npm install
ng serve
```

Open in browser:  
👉 `http://localhost:4200`

API base URL (defined in `environment.ts`) should point to:  
👉 `http://localhost:8080`

---

## 📁 Project Structure

```
src/app/
├── features/          # Feature modules: auth, feed, topics, articles, profile
├── services/          # API services
├── models/            # TypeScript interfaces
├── components/        # Shared UI components (e.g., header)
├── auth.interceptor.ts  # JWT token injection
├── app.module.ts      # Main module
└── app-routing.module.ts
```

---

## 🔗 Related Projects

- [Backend – Spring Boot (MDD)](../back/README.md)  
- [Figma Mockups](https://www.figma.com/design/Rflr3TVBog35BNMnn0DF09/Maquettes-MDD-(desktop-et-mobile))

---

## 👤 Author

**Natallia Skiba**
