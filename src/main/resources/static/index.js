document.addEventListener('DOMContentLoaded', function () {
  const forms = document.getElementById('formulario');
  const cancelar = document.getElementById('cancelar');
  const modalSeguro = document.getElementById('modal-seguro');
  
  cancelar.addEventListener('click', function () {
    modalSeguro.style.display = 'block';
    const buttonCancelarSi = document.getElementById('button-cancelar-si');
    buttonCancelarSi.addEventListener('click', function () {
      forms.reset();
      const errores = document.getElementById('errores');
      errores.innerHTML = '';
      modalSeguro.style.display = 'none';
    }
    );
    const buttonCancelarNo = document.getElementById('button-cancelar-no');
    buttonCancelarNo.addEventListener('click', function () {
      modalSeguro.style.display = 'none';
    }
    );
  });

  forms.addEventListener('submit', function (event) {
    console.log('Formulario enviado');
    const apellido = document.getElementById('apellido');
    const nombre = document.getElementById('nombre');
    //const tipoDoc = document.getElementById('tipo-doc');
    const nroDoc = document.getElementById('nro-doc');
    const fechaNac = document.getElementById('fecha-nac');
    const calle = document.getElementById('calle');
    const numero = document.getElementById('nro-calle');
    const pais = document.getElementById('pais');
    const provincia = document.getElementById('provincia');
    const codigoPostal = document.getElementById('codigo-postal');
    const localidad = document.getElementById('localidad');
    const telefono = document.getElementById('telefono');
    const ocupacion = document.getElementById('ocupacion');
    const nacionalidad = document.getElementById('nacionalidad');
    const posicionIva = document.getElementById('posicion-iva');
    const errores = document.getElementById('errores');
    const errorFormato = document.getElementById('error-formato');
    let hayError = false;
    errores.innerHTML = '';
    

    if (!apellido.value.trim()) {
      const faltaApellido = document.createElement('li');
      faltaApellido.textContent = 'El campo Apellido es obligatorio.';
      errores.appendChild(faltaApellido);
      apellido.focus();
      apellido.classList.add('campo-error') // REVISAR PARA COLOREAR EL BORDE
      hayError = true;
    } else {
      apellido.classList.remove('campo-error');
    }

    if (!nombre.value.trim()) {
      const faltaNombres = document.createElement('li');
      faltaNombres.textContent = 'El campo Nombres es obligatorio.';
      errores.appendChild(faltaNombres);
      if (!hayError) {
        nombre.focus();
        nombre.classList.add('campo-error');
        hayError = true;
      }
    } else {
      nombre.classList.remove('campo-error');
    }
    const reGexFecha = /^\d{4}-\d{2}-\d{2}$/;
    if (!fechaNac.value) {
      const faltaFechaNac = document.createElement('li');
      faltaFechaNac.textContent = 'El campo Fecha de Nacimiento es obligatorio.';
      errores.appendChild(faltaFechaNac);
      if (!hayError) {
        fechaNac.focus();
        fechaNac.classList.add('campo-error');
        hayError = true;
      }
    } else if (!reGexFecha.test(fechaNac.value)) {
        const formatoFechaNac = document.createElement('li');
        formatoFechaNac.textContent = 'El campo Fecha de Nacimiento debe tener el formato AAAA-MM-DD.';
        errorFormato.appendChild(formatoFechaNac); 
        if (!hayError) {
          fechaNac.focus();
          fechaNac.classList.add('campo-error');
          hayError = true;
        }
  } else {
      fechaNac.classList.remove('campo-error');
    }
    const reGexNroDoc = /^\d+$/;

    if (!nroDoc.value.trim()) {
      const faltaNroDoc = document.createElement('li');
      faltaNroDoc.textContent = 'El campo Número de Documento es obligatorio.';
      errores.appendChild(faltaNroDoc);
      if (!hayError) {
        nroDoc.focus();
        nroDoc.classList.add('campo-error');
        hayError = true;
      }
    } else if (!reGexNroDoc.test(nroDoc.value.trim())) {
        const formatoNroDoc = document.createElement('li');
        formatoNroDoc.textContent = 'El campo Número de Documento debe contener solo números.';
        errorFormato.appendChild(formatoNroDoc);
        if (!hayError) {
          nroDoc.focus();
          nroDoc.classList.add('campo-error');
          hayError = true;
        }
    } else {
      nroDoc.classList.remove('campo-error');
    }
    const reGexTexto = /^[A-Za-zÀ-ÿ\s]+$/;
    if (!nacionalidad.value.trim()) {
      const faltaNacionalidad = document.createElement('li');
      faltaNacionalidad.textContent = 'El campo Nacionalidad es obligatorio.';
      errores.appendChild(faltaNacionalidad);
      if (!hayError) {
        nacionalidad.focus();
        nacionalidad.classList.add('campo-error');
        hayError = true;
      }
    } else if (!reGexTexto.test(nacionalidad.value.trim())) {
        const formatoNacionalidad = document.createElement('li');
        formatoNacionalidad.textContent = 'El campo Nacionalidad debe contener solo letras y espacios.';
        errorFormato.appendChild(formatoNacionalidad);
        if (!hayError) {
          nacionalidad.focus();
          nacionalidad.classList.add('campo-error');
          hayError = true;
        }
  } else {
      nacionalidad.classList.remove('campo-error');
    }
    if (!calle.value.trim()) {
      const faltaCalle = document.createElement('li');
      faltaCalle.textContent = 'El campo Calle es obligatorio.';
      errores.appendChild(faltaCalle);
      if (!hayError) {
        calle.focus();
        calle.classList.add('campo-error');
        hayError = true;
      }
    } else if (!reGexTexto.test(calle.value.trim())) {
        const formatoCalle = document.createElement('li');
        formatoCalle.textContent = 'El campo Calle debe contener solo letras y espacios.';
        errorFormato.appendChild(formatoCalle);
        if (!hayError) {
          calle.focus();
          calle.classList.add('campo-error');
          hayError = true;
        }
  } else {
      calle.classList.remove('campo-error');
    }
    if (!numero.value.trim()) {
      const faltaNumero = document.createElement('li');
      faltaNumero.textContent = 'El campo Número de Calle es obligatorio.';
      errores.appendChild(faltaNumero);
      if (!hayError) {
        numero.focus();
        numero.classList.add('campo-error');
        hayError = true;
      }
    } else {
      numero.classList.remove('campo-error');
    }
    if (!pais.value.trim()) {
      const faltaPais = document.createElement('li');
      faltaPais.textContent = 'El campo País es obligatorio.';
      errores.appendChild(faltaPais);
      if (!hayError) {
        pais.focus();
        pais.classList.add('campo-error');
        hayError = true;
      }
    } else {
      pais.classList.remove('campo-error');
    }
    if (!provincia.value.trim()) {
      const faltaProvincia = document.createElement('li');
      faltaProvincia.textContent = 'El campo Provincia es obligatorio.';
      errores.appendChild(faltaProvincia);
      if (!hayError) {
        provincia.focus();
        provincia.classList.add('campo-error');
        hayError = true;
      }
    } else {
      provincia.classList.remove('campo-error');
    }
    if (!localidad.value.trim()) {
      const faltaLocalidad = document.createElement('li');
      faltaLocalidad.textContent = 'El campo Localidad es obligatorio.';
      errores.appendChild(faltaLocalidad);
      if (!hayError) {
        localidad.focus();
        localidad.classList.add('campo-error');
        hayError = true;
      }
    } else {
      localidad.classList.remove('campo-error');
    }
    if (!codigoPostal.value.trim()) {
      const faltaCodigoPostal = document.createElement('li');
      faltaCodigoPostal.textContent = 'El campo Código Postal es obligatorio.';
      errores.appendChild(faltaCodigoPostal);
      if (!hayError) {
        codigoPostal.focus();
        codigoPostal.classList.add('campo-error');
        hayError = true;
      }
    } else {
      codigoPostal.classList.remove('campo-error');
    }
    const reGexTelefono = /^\d+$/;
    if (!telefono.value.trim()) {
      const faltaTelefono = document.createElement('li');
      faltaTelefono.textContent = 'El campo Teléfono es obligatorio.';
      errores.appendChild(faltaTelefono);
      if (!hayError) {
        telefono.focus();
        telefono.classList.add('campo-error');
        hayError = true;
      }
    } else if (!reGexTelefono.test(telefono.value.trim())) {
        const formatoTelefono = document.createElement('li');
        formatoTelefono.textContent = 'El campo Teléfono debe contener solo números.';
        errorFormato.appendChild(formatoTelefono);
        if (!hayError) {
          telefono.focus();
          telefono.classList.add('campo-error');
          hayError = true;
        }
  } else {
      telefono.classList.remove('campo-error');
    }
    if (!ocupacion.value.trim()) {
      const faltaOcupacion = document.createElement('li');
      faltaOcupacion.textContent = 'El campo Ocupación es obligatorio.';
      errores.appendChild(faltaOcupacion);
      if (!hayError) {
        ocupacion.focus();
        ocupacion.classList.add('campo-error');
        hayError = true;
      }
    } else {
      ocupacion.classList.remove('campo-error');
    }
    if (posicionIva === '') {
      const faltaPosicionIva = document.createElement('li');
      faltaPosicionIva.textContent = 'El campo Posición IVA es obligatorio.';
      errores.appendChild(faltaPosicionIva);
      if (!hayError) {
        posicionIva.focus();
        posicionIva.classList.add('campo-error');
        hayError = true;
      }
    } else {
      posicionIva.classList.remove('campo-error');
    }

    if (hayError) {
      event.preventDefault();
      console.log('Errores en el formulario, no se envía.');
    }
  });

});