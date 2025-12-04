document.addEventListener("DOMContentLoaded", function () {
  const acciones = document.getElementById("modalAcciones");
  const botonSeguir = document.getElementById("btn-seguir");
  const botonCargarOtra = document.getElementById("btn-otra-habitacion");
  const botonSalir = document.getElementById("btn-salir");
  const queryString = window.location.search; 
  const urlParams = new URLSearchParams(queryString);
  const botonAceptar = document.getElementById("btn-aceptar");

  const nroHabitacion = urlParams.get('numeroHabitacion');
  const fechaInicio = urlParams.get('fechaInicio');
  const fechaFin = urlParams.get('fechaFin');

  botonSeguir.addEventListener("click", function () {
    acciones.hide();
  });
  botonCargarOtra.addEventListener("click", function () {
    acciones.hide();
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
    acciones.hide();
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

  let preguntar = document.getElementById("url").getAttribute("preguntar");
  preguntar = preguntar === 'true' ? true : false;
  if (preguntar) {
    acciones.show();
  }
  const listado = document.getElementById("listado-huespedes");
  const listaHuespedes = listado.querySelector('ul');
  let selectedHuesped = null;
  if (listaHuespedes) {
    listaHuespedes.addEventListener("click", function (event) {
      if (event.target && event.target.closest("li")) {
        if (!selectedHuesped) {
        selectedHuesped = event.target.closest("li");
        selectedHuesped.classList.add("selectedHuesped");
        } else {
          console.log(selectedHuesped.firstElementChild.textContent);
          selectedHuesped.classList.remove("selectedHuesped");
          selectedHuesped = null;
        }
      }
    });
  }
  botonAceptar.addEventListener("click", function () {
    if (selectedHuesped) {
      const idHuesped = selectedHuesped.getAttribute("data-id-huesped");
      const ocuparUrl = `/huesped/ocuparHabitacion?idHuesped=${idHuesped}&numeroHabitacion=${nroHabitacion}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`;
      fetch(ocuparUrl, {
        method: 'POST'
      })
      .then(response => {
        if (!response.ok) {
          console.error('Error al ocupar la habitación');
        }
      })
      .catch(error => {
        console.error('Error en la solicitud:', error);
      });
    }
  });
});