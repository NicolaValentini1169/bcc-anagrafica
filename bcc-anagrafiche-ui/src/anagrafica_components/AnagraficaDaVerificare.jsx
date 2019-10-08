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
        console.log(e.currentTarget)
        let verifica = {...this.state.verifica}

        verifica[e.currentTarget.name] = !this.state.verifica[e.currentTarget.name]

        this.setState({verifica});
     }

     confirmVerify = () => {
         let verifica = {...this.state.verifica}

         verifica.id = this.props.cliente.id;

         this.props.handleVerifyRegistry(verifica, this.props.cliente.codice);
     }

    render() { 
        const cliente = {...this.props.cliente};
        return ( 
            <React.Fragment>
                <div className="row">
                <h3 className="text-left ml-4 mt-3 mb-3 col-md-7">Cliente</h3>
                <button className="btn btn-primary ml-11 mt-3 mb-3 col-md-1 offset-md-3" onClick={() => this.props.tornaAllaLista()}>{LABELS.TORNA_ALLA_LISTA}</button>
                </div>
            <div className="verifyRegistry row">
                <label className="col-md-7 text-left font-weight-bold mt-5">{LABELS.NAG}</label>
                <label className="col-md-5 text-left font-weight-bold mt-2 underlined">{LABELS.CAMPI_MODIFICATI}</label>
                <label className="col-md-12 text-left">{cliente.nag ? cliente.nag : ""}</label>
                <label className="col-md-12 text-left font-weight-bold">{LABELS.NOME_BATTESIMO}</label>
                <label className="col-md-12 text-left">{cliente.nome ? cliente.nome : ""}</label>
                <label className="col-md-12 text-left font-weight-bold">{LABELS.DATA_DI_NASCITA}</label>
                <label className="col-md-12 text-left">{cliente.dataNascita ? <Moment format="DD/MM/YYYY">{cliente.dataNascita}</Moment> : ""}</label>
                <label className="col-md-12 text-left font-weight-bold">{LABELS.NUMERO_TELEFONO}</label>
                <label className="col-md-7 text-left">{cliente.telefono ? cliente.telefono : ""}</label>
                <input type="checkbox" className=" checkBox" name="telefono" onChange={(e) => this.onChange(e)} />
                <label className="col-md-12 text-left font-weight-bold">{LABELS.EMAIL}</label>
                <label className="col-md-7 text-left">{cliente.email ? cliente.email : ""}</label>
                <input type="checkbox" className=" checkBox" name="email" onChange={(e) => this.onChange(e)}/>
                <label className="col-md-12 text-left font-weight-bold">{LABELS.PRIVACY}</label>
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 1: {cliente.p1 ? "Si" : !cliente.p1 ? "No" : "Non impostata"}</p>
                <input type="checkbox" className=" checkBox" name="p1" onChange={(e) => this.onChange(e)}/>
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 2: {cliente.p2 ? "Si" : !cliente.p2 ? "No" : "Non impostata"}</p>
                <input type="checkbox" className=" checkBox" name="p2" onChange={(e) => this.onChange(e)} />
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 3: {cliente.p3 ? "Si" : !cliente.p3 ? "No" : "Non impostata"}</p>
                <input type="checkbox" className=" checkBox" name="p3" onChange={(e) => this.onChange(e)} />
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 4: {cliente.p4 ? "Si" : !cliente.p4 ? "No" : "Non impostata"}</p>
                <input type="checkbox" className="checkBox" name="p4" onChange={(e) => this.onChange(e)} />
                <p className="col-md-7 text-left">{LABELS.PRIVACY} 5: {cliente.p5 ? "Si" : !cliente.p5 ? "No" : "Non impostata"}</p>
                <input type="checkbox" className=" checkBox" name="p5" onChange={(e) => this.onChange(e)} />
                <p className="col-md-7 text-left mb-4">{LABELS.PRIVACY} 6: {cliente.p6 ? "Si" : !cliente.p6 ? "No" : "Non impostata"}</p>
                <input type="checkbox" className="checkBox" name="p6" onChange={(e) => this.onChange(e)} />
                <p className="col-md-7 text-left">{LABELS.FIRMA_GRAFOMETRICA} {cliente.p7 ? "Si" : !cliente.p7 ? "No" : "Non impostata"}</p>
                <input type="checkbox" className=" checkBox" name="firma" onChange={(e) => this.onChange(e)} />
                <button className="btn btn-success offset-md-8 mb-3" onClick={() => this.confirmVerify()}>{LABELS.CONFERMA}</button>
            </div>
            </React.Fragment>
         );
    }
}
 
export default AnagraficaDaVerificare;