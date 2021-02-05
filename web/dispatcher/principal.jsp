

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        Estas logeado como ${usuario.getNombre()} <br><br>
        <a href="Controlador?direccion=consulta1" target="myFrame">Mostar/Buscar</a>
        <a href="Controlador?direccion=consulta2" target="myFrame">Actualizar</a>
        <a href="Controlador?direccion=consulta3" target="myFrame">Eliminar</a>
        <a href="Controlador?direccion=consulta4" target="myFrame">Salir</a>
        <div style="heigth:700px; width: 1000px">
            <iframe name="myFrame" style="height: 500px; width: 100%;">
                
            </iframe>
        </div>
    
    </body>
</html>
