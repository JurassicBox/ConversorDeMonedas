# ConversorDeMonedas
Ejercicio Portafolio #AluraLatam
para que funcione el programa debes:
Paso 1: Crear un archivo config.properties en la ubicación correcta
Abre el Explorador de Windows
Ve a donde tengas descargado el archivo y abrelo
Crea un archivo de texto llamado config.properties (directamente ahí, no dentro de ninguna carpeta)
Abre el archivo con el Bloc de notas
Escribe exactamente esto:

propertiesAPI_KEY=tu_clave_real_aqui
IMPORTANTE: Reemplaza tu_clave_real_aqui con tu clave real de exchangerate-api.com

Paso 3: Editar el archivo config.properties
Tu archivo debe quedar así (ejemplo):
propertiesAPI_KEY=a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6
```

**Verificaciones importantes:**
- ✅ Sin espacios antes o después del `=`
- ✅ Sin comillas
- ✅ Una sola línea
- ✅ Guardado en formato `.properties` (no `.txt`)

Paso 4: Verificar que el archivo se guardó correctamente

En el Explorador de Windows:
1. Deberías ver el archivo `config.properties`
2. Si dice `config.properties.txt`, necesitas cambiar la extensión:
   - En el Explorador, ve a **Ver → Mostrar extensiones de nombre de archivo**
   - Renombra el archivo a `config.properties` (quita el `.txt`)

Paso 6: Ejecutar el programa

No necesitas hacer Rebuild (el código busca el archivo directamente)
Ejecuta el programa
Debería decir: ✓ API Key cargada desde raíz del proyecto
