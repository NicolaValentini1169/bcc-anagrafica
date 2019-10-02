import React, { Component } from 'react';
import {LABELS} from "./common/Constants";

class AnagraficaDaVerificare extends Component {
    state = {  }
    render() { 
        return ( 
            <React.Fragment>
                <h3 className="text-left ml-4 mt-3 mb-3">Cliente <button className="btn btn-primary" onClick={() => this.props.tornaAllaLista()}>{LABELS.TORNA_ALLA_LISTA}</button></h3>
            <div className="verifyRegistry row">
                <label className="col-md-7 text-left font-weight-bold mt-5">{LABELS.NAG}</label>
                <label className="col-md-5 text-left font-weight-bold mt-2 underlined">{LABELS.CAMPI_MODIFICATI}</label>
                <label className="col-md-12 text-left">XXXXXXXXXX</label>
                <label className="col-md-12 text-left font-weight-bold">{LABELS.NOME_BATTESIMO}</label>
                <label className="col-md-12 text-left">Paolo Rossi</label>
                <label className="col-md-12 text-left font-weight-bold">{LABELS.DATA_DI_NASCITA}</label>
                <label className="col-md-12 text-left">00/00/0000</label>
                <label className="col-md-12 text-left font-weight-bold">{LABELS.NUMERO_TELEFONO}</label>
                <label className="col-md-7 text-left">34XXXXXXXX</label>
                <input type="checkbox" className="col-md-1 checkBox" />
                <label className="col-md-12 text-left font-weight-bold">{LABELS.EMAIL}</label>
                <label className="col-md-7 text-left">prova@prova.it</label>
                <input type="checkbox" className="col-md-1 checkBox" />
                <label className="col-md-12 text-left font-weight-bold">{LABELS.PRIVACY}</label>
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 1: VALORE</p>
                <input type="checkbox" className="col-md-1 checkBox" />
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 2: VALORE</p>
                <input type="checkbox" className="col-md-1 checkBox" />
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 3: VALORE</p>
                <input type="checkbox" className="col-md-1 checkBox" />
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 4: VALORE</p>
                <input type="checkbox" className="col-md-1 checkBox" />
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 5: VALORE</p>
                <input type="checkbox" className="col-md-1 checkBox" />
                <p className="col-md-7 text-left mb-4">{LABELS.PRIVACY} 6: VALORE</p>
                <input type="checkbox" className="col-md-1 checkBox" />
                <p className="col-md-7 text-left">{LABELS.FIRMA_GRAFOMETRICA} VALORE</p>
                <input type="checkbox" className="col-md-1 checkBox" />
                <button className="btn btn-success col-md-1 offset-md-7 mb-3">{LABELS.CONFERMA}</button>
            </div>
            </React.Fragment>
         );
    }
}
 
export default AnagraficaDaVerificare;