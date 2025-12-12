function actualizarDatos() {
  const fechaInicio = document.getElementById("fechaInicio").value;
  const fechaFin = document.getElementById("fechaFin").value;
  const tipoHabitacion = document.getElementById("tipo").value;

  if (fechaInicio && fechaFin && tipoHabitacion) {
    if (fechaInicio > fechaFin) {
      alert("La fecha de inicio no puede ser posterior a la fecha de fin.");
      return;
    }
    const ruta = document.getElementById("ruta").textContent;
    window.location.href = `/habitaciones/${ruta}?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&tipo=${tipoHabitacion}`;
  }
}

document.addEventListener("DOMContentLoaded", function () {
  const fechaInicioInput = document.getElementById("fechaInicio");
  const fechaFinInput = document.getElementById("fechaFin");
  const tipoHabitacionSelect = document.getElementById("tipo");
  

  if (fechaInicioInput) {
    fechaInicioInput.addEventListener("change", actualizarDatos);
  }
  if (fechaFinInput) {
    fechaFinInput.addEventListener("change", actualizarDatos);
  }
  if (tipoHabitacionSelect) {
    tipoHabitacionSelect.addEventListener("change", actualizarDatos);
  }

});