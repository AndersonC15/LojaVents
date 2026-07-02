# LojaVents

## Autenticación JWT

1. Haz un `POST /auth/registro` con el cuerpo:
   ```json
   {
     "nombre": "Usuario Prueba",
     "correo": "usuario@lojavents.com",
     "contrasena": "valorSeguro123"
   }
   ```
2. Luego ejecuta un `POST /auth/login` con `correo` y `contrasena` para obtener:
   ```json
   {
     "token": "<JWT>",
     "tipo": "Bearer"
   }
   ```
3. En Insomnia (o cualquier cliente REST) agrega el header `Authorization: Bearer <JWT>` para llamar a los demás endpoints protegidos.

El backend corre por defecto en `http://localhost:8090`.
