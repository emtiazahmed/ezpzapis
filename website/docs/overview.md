---
sidebar_position: 1
---

# Overview

Ezpz APIs is a boilerplate template for modern, scalable and cloud-ready microservices architecture.

We know It's hard to build microservices that are production grade, and it takes a lot of time to harden them and make everything work together. EzPz APIs uses latest stable frameworks and libraries. If you're building microservices, it will provide you with an excellent start with all the best practices already implemented, ready to be customized and extended.

### Our Tech-stack:
- **Microservices Framework**
  - Spring Boot 3.1.4
    - Webflux
    - Spring Security
- **Datastore**
  - MongoDB
- **Service Discovery**
   - Consul
- **API Gateway**
   - Krakend
- **Identity Management, AuthN & AuthZ**
  - Keycloak
- **App / User Interface**
  - Quasar Framework (UI) - based on Vue3
- **Observability**
  - Grafana (Dashboards)
  - Loki (Log Aggregation)
  - Prometheus (Metrics)
  - Tempo (Tracing)

## Getting Started

Get started by **creating a new site**.

Or **try Docusaurus immediately** with **[docusaurus.new](https://docusaurus.new)**.

### What you'll need

- [Node.js](https://nodejs.org/en/download/) version 16.14 or above:
  - When installing Node.js, you are recommended to check all checkboxes related to dependencies.

## Generate a new site

Generate a new Docusaurus site using the **classic template**.

The classic template will automatically be added to your project after you run the command:

```bash
npm init docusaurus@latest my-website classic
```

You can type this command into Command Prompt, Powershell, Terminal, or any other integrated terminal of your code editor.

The command also installs all necessary dependencies you need to run Docusaurus.

## Start your site

Run the development server:

```bash
cd my-website
npm run start
```

The `cd` command changes the directory you're working with. In order to work with your newly created Docusaurus site, you'll need to navigate the terminal there.

The `npm run start` command builds your website locally and serves it through a development server, ready for you to view at http://localhost:3000/.

Open `docs/intro.md` (this page) and edit some lines: the site **reloads automatically** and displays your changes.
