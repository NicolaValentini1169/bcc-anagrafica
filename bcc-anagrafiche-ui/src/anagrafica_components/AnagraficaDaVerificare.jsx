import React, { Component } from 'react';
import {LABELS} from "./common/Constants";
import Moment from 'react-moment';

class AnagraficaDaVerificare extends Component {
    state = { 
        verifica: {
            "id": null,
            "telefono": false,
            "email": false,
            "p1": false,
            "p2": false,
            "p3": false,
            "p4": false,
            "p5": false,
            "p6": false,
            "firma": false
        }
     }

     onChange = (e) => {
        let verifica = {...this.state.verifica}

        verifica[e.currentTarget.name] = !this.state.verifica[e.currentTarget.name]

        this.setState({verifica});
     }

     confirmVerify = () => {
         let verifica = {...this.state.verifica}
         let cliente = {...this.props.cliente}
         verifica.id = cliente.id;

         this.props.handleVerifyRegistry(verifica, cliente.codice);
     }

    render() { 
        const {nag, nome, dataNascita, telefono, email, p1, p2, p3, p4, p5, p6, p7} = this.props.cliente;
        return ( 
            <React.Fragment>
                <div className="row">
                <h3 className="text-left ml-4 mt-3 mb-3 col-md-7">Cliente</h3>
                <button className="btn btn-primary ml-11 mt-3 mb-3 col-md-1 offset-md-3" onClick={() => this.props.tornaAllaLista()}>{LABELS.TORNA_ALLA_LISTA}</button>
                </div>
            <div className="verifyRegistry row">
                <label className="col-md-7 text-left font-weight-bold mt-5">{LABELS.NAG_TO_SHOW}</label>
                <label className="col-md-5 text-left font-weight-bold mt-2 underlined">{LABELS.CAMPI_MODIFICATI}</label>
                <label className="col-md-12 text-left">{nag ? nag : ""}</label>

                <label className="col-md-12 text-left font-weight-bold">{LABELS.NOME_BATTESIMO}</label>
                <label className="col-md-12 text-left">{nome ? nome : ""}</label>

                <label className="col-md-12 text-left font-weight-bold">{LABELS.DATA_DI_NASCITA}</label>
                <label className="col-md-12 text-left">{dataNascita ? <Moment format="DD/MM/YYYY">{dataNascita}</Moment> : ""}</label>

                <label className="col-md-12 text-left font-weight-bold">{LABELS.NUMERO_TELEFONO}</label>
                <label className="col-md-7 text-left">{telefono ? telefono : ""}</label>
                <input type="checkbox" className=" checkBox" name="telefono" onChange={(e) => this.onChange(e)} />

                <label className="col-md-12 text-left font-weight-bold">{LABELS.EMAIL}</label>
                <label className="col-md-7 text-left">{email ? email : ""}</label>
                <input type="checkbox" className=" checkBox" name="email" onChange={(e) => this.onChange(e)}/>

                <label className="col-md-12 text-left font-weight-bold">{LABELS.PRIVACY}</label>
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 1: {p1 ? LABELS.SI : !p1 ? LABELS.NO : LABELS.NON_IMPOSTATA}</p>
                <input type="checkbox" className=" checkBox" name="p1" onChange={(e) => this.onChange(e)}/>

                <p className="col-md-7 text-left">{LABELS.PRIVACY} 2: {p2 ? LABELS.SI : !p2 ? LABELS.NO : LABELS.NON_IMPOSTATA}</p>
                <input type="checkbox" className=" checkBox" name="p2" onChange={(e) => this.onChange(e)} />

                <p className="col-md-7 text-left">{LABELS.PRIVACY} 3: {p3 ? LABELS.SI : !p3 ? LABELS.NO : LABELS.NON_IMPOSTATA}</p>
                <input type="checkbox" className=" checkBox" name="p3" onChange={(e) => this.onChange(e)} />

                <p className="col-md-7 text-left">{LABELS.PRIVACY} 4: {p4 ? LABELS.SI : !p4 ? LABELS.NO : LABELS.NON_IMPOSTATA}</p>
                <input type="checkbox" className="checkBox" name="p4" onChange={(e) => this.onChange(e)} />

                <p className="col-md-7 text-left">{LABELS.PRIVACY} 5: {p5 ? LABELS.SI : !p5 ? LABELS.NO : LABELS.NON_IMPOSTATA}</p>
                <input type="checkbox" className=" checkBox" name="p5" onChange={(e) => this.onChange(e)} />

                <p className="col-md-7 text-left mb-4">{LABELS.PRIVACY} 6: {p6 ? LABELS.SI : !p6 ? LABELS.NO : LABELS.NON_IMPOSTATA}</p>
                <input type="checkbox" className="checkBox" name="p6" onChange={(e) => this.onChange(e)} />

                <p className="col-md-7 text-left">{LABELS.FIRMA_GRAFOMETRICA} {p7 ? LABELS.SI : !p7 ? LABELS.NO : LABELS.NON_IMPOSTATA}</p>
                <input type="checkbox" className=" checkBox" name="firma" onChange={(e) => this.onChange(e)} />

                <button className="btn btn-success offset-md-8 mb-3" onClick={() => this.confirmVerify()}>{LABELS.CONFERMA}</button>
            </div>
            </React.Fragment>
         );
    }
}
 
export default AnagraficaDaVerificare;