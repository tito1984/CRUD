
# Simple CRUD

A simple CRUD with posts and comments with one-to-many association and unit ant integration test




## Badges

![Static Badge](https://img.shields.io/badge/Java-17-red)
![Static Badge](https://img.shields.io/badge/Spring%20Boot-3.1.0-green)


## Authors

- [@tito1984](https://www.github.com/tito1984)


## Application Properties

To run this project, you will need to add the following variables.

For MySQL

`spring.datasource.url`

`spring.datasource.username`

`spring.datasource.password`

`spring.jpa.properties.hibernate.dialect`

For H2 database

`spring.datasource.username`

`spring.datasource.password`


## Deployment

Just clone the repository and change the application.properties values from your own.

You can use MySql and H2 databases at your choise

To start project just type this into the terminal

```bash
  mvnw spring-boot:run
```


## Features

- 2 Entities Publications and Comments
- One-to-many relation between them
- Repository and Service interfaces
- Pagination for Publications
- Individual Controllers for each entity
- Unit and integration test for both Entities


## API Reference

#### Get all publications

```http
  GET /api/publications
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `pageNo`      | `int` | Page number, default value '0' |
| `pageSize`      | `int` | Page size, default value '10' |
| `sortBy`      | `string` | Page number, default value 'id' |
| `sortDir`      | `string` | Page number, default value 'asc' |

#### Get publication

```http
  GET /api/publications/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |

#### Create publication

```http
  POST /api/publications
```
Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Required**. publication title |
| `description`      | `string` | **Required**. publication description |
| `content`      | `string` | **Required**. publication content |

#### Edit publication

```http
  PUT /api/publications/${id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |

Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Required**. publication title |
| `description`      | `string` | **Required**. publication description |
| `content`      | `string` | **Required**. publication content |

#### Delete publication

```http
  DELETE /api/publications/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |

#### Get all comments from publication

```http
  GET /api/publications/{publicationId}/comments
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `publicationId`      | `long` | **Required**. Id of publication |

#### Get publication

```http
  GET /api/publications/{publicationId}/comments/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of comment to fetch |
| `publicationId`      | `long` | **Required**. Id of publication |

#### Create publication

```http
  POST /api/publications/{publicationId}/comments
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `publicationId`      | `long` | **Required**. Id of publication |

Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. publication name |
| `email`      | `string` | **Required**. publication email |
| `body`      | `string` | **Required**. publication body |

#### Edit publication

```http
  PUT /api/publications/{publicationId}/comments/{id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of comment to fetch |
| `publicationId`      | `long` | **Required**. Id of publication |

Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. publication name |
| `email`      | `string` | **Required**. publication email |
| `body`      | `string` | **Required**. publication body |

#### Delete publication

```http
  DELETE /api/publications/{publicationId}/comments/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of comment to fetch |
| `publicationId`      | `long` | **Required**. Id of publication |