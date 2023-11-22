FROM node:21.2.0

WORKDIR /frontend

ENV PATH /frontend/node_modules/.bin:$PATH

# COPY package.json ./
# COPY package-lock.json ./

COPY . /frontend

RUN npm i

CMD ["npm", "run", "dev"]