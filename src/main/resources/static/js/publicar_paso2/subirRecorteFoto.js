
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
            aspectRatio: 1,
            preview: '.img-sample',
            zoomable: false,
            viewMode: 1,
            responsive: true,
            dragMode: 'move',
            ready() {
                // Se adapta al modal
                let modal = document.querySelector('.modal');
                let modalBody = modal.querySelector('.modal-body');
                let modalBodyWidth = modalBody.offsetWidth;
                let modalBodyHeight = modalBody.offsetHeight;

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

    // Al subir la imagen el modal se cierra
    $('#subirImgModal').modal('hide');
});
