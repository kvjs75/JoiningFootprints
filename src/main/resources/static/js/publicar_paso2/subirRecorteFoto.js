
let cropper;

$('#subirFoto').on('change', () => {
    let imagen = document.getElementById('img-cropper');
    let entrada = document.getElementById('subirFoto');

    let archivos = entrada.files;

    let extensiones = entrada.value.substring(entrada.value.lastIndexOf('.'), entrada.value.length);

    // Si ya existe un cropper, lo borra para crear uno nuevo
    if (cropper) {
        cropper.destroy();
    }

    // Impedir que el usuario suba otra cosa que no sea una imagen
    if (!archivos || !archivos.length) {
        imagen.src = "";
        entrada.value = "";
    } else if (entrada.getAttribute('accept').split(',').indexOf(extensiones) < 0) {
        alert("No es una imagen");
        entrada.value = "";
    } else {
        let imagenUrl = URL.createObjectURL(archivos[0]);
        imagen.src = imagenUrl;

        // Si todo está bien, se ejecuta el cropper
        cropper = new Cropper(imagen, {
            aspectRatio: 1, //Establece la relacion de aspecto del area de recorte como un cuadrado
            preview: '.img-sample', //Selector CSS para el elemento donde se mostrara una vista previa del area de recorte
            zoomable: false, //Permite o deshabilita la capacidad de hacer zoom en la imagen
            viewMode: 1, //Define como se debe mostrar la imagen dentro del area de recorte
            responsive: true, //Permite que el Cropper se ajuste automaticamente al cambiar el tamaño de la ventana del navegador
            dragMode: 'move', //Define el modo de arrastre del Cropper
            ready() {
                //Se adapta al tamaño del modal
                let modal = document.querySelector('.modal');
                let modalBody = modal.querySelector('.modal-body');
                let modalBodyWidth = modalBody.offsetWidth;
                let modalBodyHeight = modalBody.offsetHeight;

                //Establece el tamaño del area de recorte para que coincida con el tamaño del modal
                cropper.setCropBoxData({
                    width: modalBodyWidth,
                    height: modalBodyHeight
                });
            }
        });
    }
});

//Al cancelar o cerrar se vacia todo y el cropper se destruye
$('#cancelar').on('click', () => {

    let imagen = document.getElementById('img-cropper');
    let entrada = document.getElementById('subirFoto');

    imagen.src = "";
    entrada.value = "";
    cropper.destroy();
});

$('#botonClose').on('click', () => {

    let imagen = document.getElementById('img-cropper');
    let entrada = document.getElementById('subirFoto');

    imagen.src = "";
    entrada.value = "";
    cropper.destroy();

});

//Al aceptar con la imagen subida se guarda el recorte donde esta el div correspondiente
$('#aceptar').on('click', function(event) {
    // Prevenir el comportamiento predeterminado del botón en un formulario
    event.preventDefault(); 

    let imagenContenedor = document.getElementById('fotoAnimalDesaparecido');
    let recorteSeleccion = cropper.getCroppedCanvas();

    recorteSeleccion.toBlob(function(blob) {
        let urlRecorte = URL.createObjectURL(blob)
        imagenContenedor.src = urlRecorte;
    });

    let imagen = document.getElementById('img-cropper');
    let entrada = document.getElementById('subirFoto');

    imagen.src = "";
    entrada.value = "";
    cropper.destroy();

    // Al subir la imagen el modal se oculta
    $('#subirImgModal').modal('hide');
});
