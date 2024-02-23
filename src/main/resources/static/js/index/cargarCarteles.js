$(document).ready(function() {
    //cantidad de carteles a cargar por pagina
    let cantidad = 5; 
    let offset = 0; 
    
    //variable de busqueda, al principio no se esta buscando nada
    let terminoBusqueda = null;
    
    const btnAnterior = document.getElementById('btnAnterior');
    const btnSiguiente = document.getElementById('btnSiguiente');


    //funcion para cargar los carteles
    function cargarCarteles() {
        $.ajax({
            url: '/masCarteles',
            method: 'GET',
            data: {
                cantidad: cantidad,
                offset: offset,
                terminoBusqueda: terminoBusqueda
            },
            success: function(data) {
                //limpia la lista de carteles antes de añadir los nuevos
                $('#listaCarteles').empty();

                //oculta el botón anterior en la primera pagina
                if (offset === 0) {
                    btnAnterior.hidden = true;
                } else {
                    btnAnterior.hidden = false;
                }

                //añade los nuevos carteles a la lista
                $('#listaCarteles').html(data);

                //verifica si la proxima pagina esta vacia despues de añadir los carteles
                verificarProximaPaginaVacia(terminoBusqueda);
                
                //devolver al usuario al principio de la pagina
                window.scrollTo({ top: 0, behavior: 'instant' });
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }

    //funcion para verificar si la proxima pagina esta vacia
    function verificarProximaPaginaVacia(terminoBusqueda) {
        $.ajax({
            url: '/masCarteles',
            method: 'GET',
            data: {
                cantidad: cantidad,
                //se comprueba que cantidad de li queda en el proxima pagina
                offset: offset + cantidad,
                terminoBusqueda: terminoBusqueda
            },
            success: function(data) {
				//si no queda ningun cartel en la proxima pagina
                let proximaPaginaVacia = $(data).filter('li').length === 0;
                //Se ocultara el boton de siguiente
                if (proximaPaginaVacia) {
                    btnSiguiente.hidden = true;
                } 
                //si no el boton de siguiente seguira viendose
                else {
                    btnSiguiente.hidden = false;
                }
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }

    //añade los carteles iniciales al cargar la pagina
    cargarCarteles();
    
    //Buscador de carteles
  	$('#buscador').keyup(function() {
		//Si ha escrito algo en la busqueda, entonces la controladora ya sabe que esta usando el buscador
        terminoBusqueda = $(this).val();
        //Reinicia "offset" al realizar una nueva búsqueda
        offset = 0; 
        cargarCarteles();
    });
    
    //boton siguiente
    $('#btnSiguiente').click(function() {
		//Incrementa en 5 el "offset" al darle siguiente"
        offset += cantidad;
        cargarCarteles();
    });

    //boton anterior
    $('#btnAnterior').click(function() {
		//disminuye en 5 el "offset" al darle anterior
        offset -= cantidad;
        
        //el "offset" no puede alcanzar numeros negarivos
        if (offset < 0) {
            offset = 0;
        }
        cargarCarteles();
    });
});