FROM node:18-slim

RUN mkdir -p /app
WORKDIR /app
COPY . /app

RUN npm install
RUN npm run build

EXPOSE 3111

ENTRYPOINT npm run serve -- --build --port 3111 --host 0.0.0.0