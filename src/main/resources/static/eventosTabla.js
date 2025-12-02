document.addEventListener("DOMContentLoaded", function () {
  const tabla = document.getElementById("tabla-estados");
  if (tabla) {
    tabla.addEventListener("click", function (event) {
      const tdSeleccionado = event.target.closest("td");
      if (tdSeleccionado) {
        const inputOculto = tdSeleccionado.querySelector('input[type="hidden"]');

        const trPadre = tdSeleccionado.parentElement;
        const firstTd = trPadre.firstElementChild;
        if (firstTd === tdSeleccionado) {
          // No hacer nada si se hace clic en la primera columna
          return;
        }
        console.log("Texto: " + tdSeleccionado.textContent.trim(''));
        console.log("Habitación: " + inputOculto.value);
        console.log("Fecha: " + tdSeleccionado.parentElement.firstElementChild.textContent);
        tdSeleccionado.style.backgroundColor = tdSeleccionado.style.backgroundColor === "yellow" ? "" : "yellow";
      }
    });
  }
});