import React, { Component } from 'react';
import DatePicker from 'react-date-picker';
import Select from 'react-select'
import AnagraficaDaVerificare from './AnagraficaDaVerificare';
import {LABELS} from "./common/Constants";
import Moment from 'react-moment';
import moment from 'moment';

export class RicercaClienti extends Component {
    state = { 
        isSingleAndNotConfirmed: false,
        isConfermata: false,
        ricerca: {
            date: null,
            nome: "",
            nag: "",
            filiale: ""
        },
        clienti: [],
        showListaClienti: false,
        cliente: {},
        nagError: false
    }

    componentWillMount() {
        this.props.handleFindFiliali();
    }

    componentWillReceiveProps(props) {
        console.log("nuove props", props)
        if(props.clienti !== undefined && props.clienti.length !== 0){
            this.setState({clienti: props.clienti, showListaClienti: true})
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

    onChangeFiliale = (filiale) => {
        console.log(filiale)
        let ricerca = {...this.state.ricerca};

        ricerca["filiale"] = filiale.value;

        this.setState({ricerca});
    }

    findCliente = () => {
        let ricerca = {...this.state.ricerca}
        
        if(ricerca.date !== null){ricerca.date = moment(ricerca.date).format("MM/DD/YYYY")}
        if(ricerca.nag.length >= 3){
            this.setState({nagError: false});
            this.props.handleFindCliente(ricerca);
        } else {
            this.setState({nagError: true});
        }
    }

    getCliente = (cliente) => {
        if(cliente.confermato)
        this.setState({cliente: cliente, showListaClienti: false, isConfermata: true })
        if(!cliente.confermato)
        this.setState({cliente: cliente, showListaClienti: false, isSingleAndNotConfirmed: true })
    }

    tornaAllaLista = () => {
        this.setState({showListaClienti: true, isConfermata: false, isSingleAndNotConfirmed: false});
    }

    render() { 
        return ( 
            <div>
            {/* <Navbar username={this.props.username}/> */}
            <h2 className="text-left ricercaClienti">{LABELS.RICERCA_CLIENTE}</h2>
            <form className={!this.state.nagError ? "formRicercaClienti" : "formRicercaClientiError"}>
                <div className="row">
                    <label className="col-1 labelForm">{LABELS.FILIALE}</label>
                    <Select className="col-md-2" placeholder="Seleziona Filiale" options={
                        this.props.filiali && this.props.filiali.length !== 0 && this.props.filiali.map(filiale => {
                            return {
                                value: filiale.id,
                                label: filiale.nome
                            }
                        })
                    }
                    onChange={filiale => this.onChangeFiliale(filiale)}/>
                    
                    <label className="col-1 labelForm">{LABELS.NAG}</label>
                    <input className={`col-md-2 form-control ${this.state.nagError ? "errorInput" : ""}`} placeholder="NAG NUMBER" name="nag" minLength={6} onChange={(e) => this.onChangeText(e)} value={this.state.ricerca.nag}/>
                    <label className="col-1 labelForm">{LABELS.NOME}</label>
                    <input className="col-md-2 form-control" placeholder="NOME CLIENTE" name="nome" onChange={(e) => this.onChangeText(e)} value={this.state.ricerca.nome}/>
                    <label className="col-md-1 labelForm">{LABELS.DATA_DI_NASCITA}</label>
                    <DatePicker
                        className="col-md-1"
                        onChange={this.onChange}
                        value={this.state.ricerca.date}
                    />
                </div>
                {this.state.nagError ? <React.Fragment><div className="row"><span className="text-danger col-md-10">Il nag deve essere di almeno tre caratteri</span></div> <div > <button type="button" className="btn btn-success bottoneRicerca" onClick={() => this.findCliente()}>{LABELS.CERCA}</button></div></React.Fragment>
                : <button type="button" className="btn btn-success bottoneRicerca" onClick={() => this.findCliente()}>{LABELS.CERCA}</button>}
            
            </form>

            {this.state.clienti.length !== 0 && this.state.showListaClienti ?
            <table class="table table-striped tableClienti">
                <thead>
                <tr>
                    <th scope="col">Cab</th>
                    <th scope="col">Nag</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Data di Nascita</th>
                    <th scope="col">Dettaglio</th>
                  </tr>
                </thead>
                <tbody>
             {this.state.clienti.map(cliente => {
                 return( <tr>
                    <td>{cliente.cab ? cliente.cab : ""}</td>
                    <td>{cliente.nag ? cliente.nag : ""}</td>
                    <td>{cliente.nome ? cliente.nome : ""}</td>
                    <td>{cliente.dataNascita ? <Moment format="DD/MM/YYYY">{cliente.dataNascita}</Moment> : ""}</td>
                    <td><button className="btn btn-light bottoneDettaglio" onClick={() => this.getCliente(cliente)}>{LABELS.DETTAGLIO}</button></td>
                    
                  </tr>
             )})} </tbody>
              </table>: ""} 

            {/* ANAGRAFICA CONFERMATA */}
            {this.state.isConfermata ? 
            <div className="text-left bottoneRicerca">
                <h2 className="col-md-2 offset-md-3">{LABELS.ATTENZIONE}</h2>
                <p className="col-md-3 offset-md-3">{LABELS.ANAGRAFICA_CLIENTE_TEXT}</p>
                <p className="col-md-2 offset-md-3">{LABELS.DATA_INSERITA} {" "} {<Moment format="DD/MM/YYYY">{this.state.cliente.lastModify}</Moment>}</p>
                <p className="col-md-2 offset-md-3">{LABELS.CODICE_UNIVOCO} {" "} {this.state.cliente.codice}</p>
                <button type="button" className="btn btn-primary col-md-1 offset-md-3" onClick={() => this.props.downloadFile()}>{LABELS.SCARICA}</button>
                <button className="btn btn-primary col-md-1 offset-1" onClick={() => this.tornaAllaLista()}>{LABELS.TORNA_ALLA_LISTA}</button>
                </div>
                : ""}

            {this.state.isSingleAndNotConfirmed ? <AnagraficaDaVerificare tornaAllaLista={this.tornaAllaLista} cliente={this.state.cliente} handleVerifyRegistry={this.props.handleVerifyRegistry}/> : ""}
            </div>
         );
    }
}
 