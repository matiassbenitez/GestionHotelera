document.addEventListener("DOMContentLoaded", function () {
  const acciones = document.getElementById("modalAcciones");
  const botonSeguir = document.getElementById("btn-seguir");
  const botonCargarOtra = document.getElementById("btn-otra-habitacion");
  const botonSalir = document.getElementById("btn-salir");
  const queryString = window.location.search; 
  const urlParams = new URLSearchParams(queryString);

  const nroHabitacion = urlParams.get('numeroHabitacion');
    const fechaInicio = urlParams.get('fechaInicio');

  botonCargarOtra.addEventListener("click", function () {
    acciones.hide();
  });
  botonSalir.addEventListener("click", function () {
    window.location.href = "/";
  });
  botonSeguir.addEventListener("click", function () {
    acciones.hide();
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
});