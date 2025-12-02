document.addEventListener("DOMContentLoaded", function () {
  let contador = 0;
  let seleccionados = [];
   
  const tabla = document.getElementById("tabla-estados");
  if (tabla) {
    tabla.addEventListener("click", function (event) {
      console.log("Click en la tabla");
      console.log("Contador: " + contador);
      const tdSeleccionado = event.target.closest("td");
      if (tdSeleccionado) {
        seleccionados.push(tdSeleccionado);
        const inputOculto = tdSeleccionado.querySelector('input[type="hidden"]');
        const trPadre = tdSeleccionado.parentElement;
        const firstTd = trPadre.firstElementChild;
        if (firstTd === tdSeleccionado) {
          return;
        }
        tdSeleccionado.classList.add("seleccion-reserva");
        contador++;
        if (contador == 2) {
          let seleccionado1 = seleccionados[0];
          let seleccionado2 = seleccionados[1];
          console.log(seleccionado1);
          console.log(seleccionado2);
          const inputOculto1 = seleccionado1.querySelector('input[type="hidden"]');
          const inputOculto2 = seleccionado2.querySelector('input[type="hidden"]');
          if (inputOculto1.value != inputOculto2.value) { // Habitaciones diferentes
            contador = 0;
            seleccionado1.classList.remove("seleccion-reserva");
            seleccionado2.classList.remove("seleccion-reserva");
            seleccionados = [];
            alert("No puede seleccionar dos habitaciones diferentes.");
          } else { //Habitaciones iguales
            const indiceColumna = seleccionado1.cellIndex;
            const tabla = seleccionado1.closest("table");
            const filas = tabla.rows;
            let todasDisponibles = true;
            const firstTd1 = seleccionado1.parentElement.firstElementChild;
            const firstTd2 = seleccionado2.parentElement.firstElementChild;
            const fecha1 = new Date(firstTd1.textContent.trim(''));
            const fecha2 = new Date(firstTd2.textContent.trim(''));
            if (fecha1 > fecha2) {
              console.log("Intercambiando s1 y s2:", seleccionado1, seleccionado2);
              [seleccionado1, seleccionado2] = [seleccionado2, seleccionado1];
              console.log("Intercambiados s1 y s2:", seleccionado1, seleccionado2);
            }
            //const fila = seleccionados[0].parentElement;
            //console.log("Rango de filas:", fila.rowIndex, "a", seleccionados[1].parentElement.rowIndex);
            let inicio = Math.min(seleccionado1.parentElement.rowIndex, seleccionado2.parentElement.rowIndex);
            let fin = Math.max(seleccionado1.parentElement.rowIndex, seleccionado2.parentElement.rowIndex);
            console.log("Rango de filas:", inicio, "a", fin);
          
            for (let i = inicio; i <= fin; i++) {
              const celda = filas[i].cells[indiceColumna];
              if (!celda.classList.contains("LIBRE")) {
                todasDisponibles = false;
                alert("Todas las fechas seleccionadas deben estar disponibles.");
                contador = 0;
                seleccionado1.classList.remove("seleccion-reserva");
                seleccionado2.classList.remove("seleccion-reserva");
                seleccionados = [];
                break;
              }
            }
            if (todasDisponibles) {
              for (let i = ++inicio; i < fin; i++) {
                const celda = filas[i].cells[indiceColumna];
                celda.classList.add("seleccion-reserva");
              }
              seleccionados = [];
              contador = 0;
            }
          }
        }
      }    
    });
  }
});