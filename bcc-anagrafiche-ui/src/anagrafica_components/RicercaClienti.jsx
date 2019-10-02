import React, { Component } from 'react';
import DatePicker from 'react-date-picker';
import Select from 'react-select'
import AnagraficaDaVerificare from './AnagraficaDaVerificare';
import Navbar from './Navbar';
import {LABELS} from "./common/Constants";

export class RicercaClienti extends Component {
    state = { 
        isSingleAndNotConfirmed: true,
        isConfermata: false,
        ricerca: {
            date: new Date(),
            nome: "",
            nag: "",
            filiale: ""
        }
    }

    onChange = date => {
        let ricerca = {...this.state.ricerca};

        ricerca.date = date;

        this.setState({ ricerca });
    }

    onChangeText = (text) => {
        let ricerca = {...this.state.ricerca};

        ricerca[text.currentTarget.name] = text.currentTarget.value;

        this.setState({ricerca});
    }

    findCliente = () => {
        let ricerca = {...this.state.ricerca}

        this.props.handleFindCliente(ricerca);
    }

    render() { 
        return ( 
            <div>
            <Navbar />
            <h2 className="text-left ricercaClienti">{LABELS.RICERCA_CLIENTE}</h2>
            <form className="formRicercaClienti">
                <div className="row">
                    <label className="col-1 labelForm">{LABELS.FILIALE}</label>
                    <Select className="col-md-2" placeholder="Seleziona Filiale"/>{/*MAP VALORI IN OPTIONS*/}
                    
                    <label className="col-1 labelForm">{LABELS.NAG}</label>
                    <input className="col-md-2 form-control" placeholder="NAG NUMBER" name="nag" minLength={6} onChange={(e) => this.onChangeText(e)} value={this.state.ricerca.nag}/>
                    <label className="col-1 labelForm">{LABELS.NOME}</label>
                    <input className="col-md-2 form-control" placeholder="NOME CLIENTE" name="nome" onChange={(e) => this.onChangeText(e)} value={this.state.ricerca.nome}/>
                    <label className="col-md-1 labelForm">{LABELS.DATA_DI_NASCITA}</label>
                    <DatePicker
                        className="col-md-1"
                        onChange={this.onChange}
                        value={this.state.ricerca.date}
                    />
                </div>
            <button type="button" className="btn btn-success bottoneRicerca" onClick={() => this.findCliente()}>{LABELS.CERCA}</button>
            </form>

            {/* CONDIZIONE SE LISTA DI CLIENTI */}
            {/* MAP MOSTRO TABELLA */}
            {/* RICORDARSI DETTAGLIO */}

            {/* ANAGRAFICA CONFERMATA */}
            {this.state.isConfermata ? 
            <div className="text-left bottoneRicerca">
                <h2 className="col-md-2 offset-md-3">{LABELS.ATTENZIONE}</h2>
                <p className="col-md-3 offset-md-3">{LABELS.ANAGRAFICA_CLIENTE_TEXT}</p>
                <p className="col-md-2 offset-md-3">{LABELS.DATA_INSERITA}</p>
                <p className="col-md-2 offset-md-3">{LABELS.CODICE_UNIVOCO}</p>
                <button type="button" className="btn btn-primary col-md-1 offset-md-4">{LABELS.STAMPA}</button>
                </div>
                : ""}

            {this.state.isSingleAndNotConfirmed ? <AnagraficaDaVerificare /> : ""}
            </div>
         );
    }
}
 