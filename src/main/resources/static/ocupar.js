document.addEventListener("DOMContentLoaded", function () {
  const acciones = document.getElementById("modalAcciones");
  const botonSeguir = document.getElementById("btn-seguir");
  const botonCargarOtra = document.getElementById("btn-otra-habitacion");
  const botonSalir = document.getElementById("btn-salir");
  const queryString = window.location.search; 
  const urlParams = new URLSearchParams(queryString);
  const botonAceptar = document.getElementById("btn-aceptar");
  const bsModalAcciones = new bootstrap.Modal(acciones);

  const nroHabitacion = urlParams.get('numeroHabitacion');
  const fechaInicio = urlParams.get('fechaInicio');
  const fechaFin = urlParams.get('fechaFin');

const myModal = document.querySelector('.modal');
if (myModal) {
  myModal.addEventListener('hide.bs.modal', function () {
    if (document.activeElement) {
      document.activeElement.blur();
    }
  });
}

  botonSeguir.addEventListener("click", function () {
    bsModalAcciones.hide();
  });
  botonCargarOtra.addEventListener("click", function () {
    bsModalAcciones.hide();
    const cargaOtraUrl = `/habitacion/cambiarEstado?numeroHabitacion=${nroHabitacion}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&cargarOtro=true`;
    fetch(cargaOtraUrl, {
      method: 'POST'
    })
    .then(response => {
      if (!response.ok) {
        console.error('Error al cambiar el estado de la habitación');
      }
    })
    .catch(error => {
      console.error('Error en la solicitud:', error);
    });

  });
  botonSalir.addEventListener("click", function () {
    bsModalAcciones.hide();
    const ocuparUrl = `/habitacion/cambiarEstado?numeroHabitacion=${nroHabitacion}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&salir=true`;
    fetch(ocuparUrl, {
      method: 'POST'
    })
    .then(response => {
      if (!response.ok) {
        console.error('Error al cambiar el estado de la habitación');
      }
    })
    .catch(error => {
      console.error('Error en la solicitud:', error);
    });

  });

  let preguntar = urlParams.get('preguntar');
  console.log("Valor original de preguntar:", preguntar);
  preguntar = preguntar == 'true' ? true : false;
  console.log("Valor de preguntar:", preguntar);
  if (preguntar) {
    bsModalAcciones.show();
  }
  console.log(bsModalAcciones);
  const listado = document.getElementById("listado-huespedes");
  const listaHuespedes = listado.querySelector('ul');
  let selectedHuesped = null;
  if (listaHuespedes) {
    listaHuespedes.addEventListener("click", function (event) {
      if (event.target && event.target.closest("li")) {
        if (!selectedHuesped) {
        selectedHuesped = event.target.closest("li");
        selectedHuesped.classList.add("selectedHuesped");
        console.log(selectedHuesped.firstElementChild.textContent);
        } else {
          selectedHuesped.classList.remove("selectedHuesped");
          selectedHuesped = null;
        }
      }
    });
  }
  botonAceptar.addEventListener("click", function () {
    if (selectedHuesped) {
      console.log("Huésped seleccionado:", selectedHuesped);
      const idHuesped = selectedHuesped.firstElementChild.textContent.trim();
      console.log("ID Huésped:", idHuesped);
      const cargaOtraUrl = `/huesped/buscarOcupantes?idHuesped=${idHuesped}&numeroHabitacion=${nroHabitacion}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`;
    fetch(cargaOtraUrl, {
      method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
      if (data.status === 'success' && data.redirectUrl) {
        console.log('Redirigiendo a:', data.redirectUrl);
        window.location.href = data.redirectUrl;
      } else {
        console.error('Error al ocupar la habitación');
      }
    }) .catch(error => {
      console.error('Error en la solicitud:', error);
    });
    }
  });
});