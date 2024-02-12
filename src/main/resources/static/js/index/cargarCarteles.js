$(document).ready(function() {
    //cantidad de carteles a cargar por pagina
    var cantidad = 5; 
    var offset = 0; 

    //funcion para cargar los carteles
    function cargarCarteles() {
        $.ajax({
            url: '/masCarteles',
            method: 'GET',
            data: {
                cantidad: cantidad,
                offset: offset
            },
            success: function(data) {
                //limpia la lista de carteles antes de añadir los nuevos
                $('#listaCarteles').empty();

                //oculta el botón anterior en la primera pagina
                if (offset === 0) {
                    $('#btnAnterior').hide();
                } else {
                    $('#btnAnterior').show();
                }

                //añade los nuevos carteles a la lista
                $('#listaCarteles').html(data);

                //verifica si la proxima pagina esta vacia despues de añadir los carteles
                verificarProximaPaginaVacia();
                
                //devolver al usuario al principio de la pagina
                window.scrollTo({ top: 0, behavior: 'instant' });
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }

    //funcion para verificar si la proxima pagina esta vacia
    function verificarProximaPaginaVacia() {
        $.ajax({
            url: '/masCarteles',
            method: 'GET',
            data: {
                cantidad: cantidad,
                offset: offset + cantidad
            },
            success: function(data) {
                var proximaPaginaVacia = $(data).filter('li').length === 0;
                //oculta o muestra el boton siguiente segun la verificacion
                if (proximaPaginaVacia) {
                    $('#btnSiguiente').hide();
                } else {
                    $('#btnSiguiente').show();
                }
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }

    //añade los carteles iniciales al cargar la pagina
    cargarCarteles();

    //boton siguiente
    $('#btnSiguiente').click(function() {
        offset += cantidad;
        cargarCarteles();
    });

    //boton anterior
    $('#btnAnterior').click(function() {
        offset -= cantidad;
        if (offset < 0) {
            offset = 0;
        }
        cargarCarteles();
    });
});