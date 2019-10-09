import React, { Component } from 'react';
import DatePicker from 'react-date-picker';
import Select from 'react-select'
import AnagraficaDaVerificare from './AnagraficaDaVerificare';
import {LABELS} from "./common/Constants";
import Moment from 'react-moment';
import moment from 'moment';

export class RicercaClienti extends Component {
    state = { 
        isNotConfermata: false,
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
        nagError: false,
        filialeError: null
    }

    componentWillMount() {
        this.props.handleFindFiliali();
    }

    componentWillReceiveProps(props) {
        if(props.clienti !== undefined && props.clienti.length !== 0 
            && (this.state.isConfermata === false && this.state.isNotConfermata === false && this.state.showListaClienti === false)){
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
        if(text.currentTarget.name === "nag" && text.currentTarget.value.length >= 6)
        this.setState({ricerca, nagError: false});
        else
        this.setState({ricerca});
    }

    onChangeFiliale = (filiale) => {
        let ricerca = {...this.state.ricerca};

        ricerca["filiale"] = filiale.value;

        this.setState({ricerca, filialeError: filiale.value !== "" ? false : true});
    }

    findCliente = () => {
        let ricerca = {...this.state.ricerca}
        
        if(ricerca.date !== null){ricerca.date = moment(ricerca.date).format("MM/DD/YYYY")}
        if(ricerca.nag.length >= 3 && this.state.ricerca.filiale !== ""){
            this.setState({nagError: false, isConfermata: false, isNotConfermata: false, showListaClienti: false, filialeError: null});
            this.props.handleFindCliente(ricerca);
        } else {
            ricerca.nag.length >= 3 && ricerca.filiale === "" ? this.setState({filialeError: true}) 
            : ricerca.nag.length < 3 && ricerca.filiale === "" ? this.setState({nagError: true, filialeError: true}) 
            : this.setState({nagError: true});
        }
    }

    getCliente = (cliente) => {
        if(cliente.confermato)
        this.setState({cliente: cliente, showListaClienti: false, isConfermata: true })
        if(!cliente.confermato)
        this.setState({cliente: cliente, showListaClienti: false, isNotConfermata: true })
    }

    tornaAllaLista = () => {
        this.setState({showListaClienti: true, isConfermata: false, isNotConfermata: false});
    }

    renderButtonCerca = () => {
        return ( <div className="row">
        <span className="col-md-2 ml-3 mt-4 text-left">* Campi obbligatori</span>
        <button type="button" className="btn btn-success col-md-1 offset-md-3 bottoneRicerca" onClick={() => this.findCliente()}>{LABELS.CERCA}</button></div>)
    }

    renderSpanNag = () => {
        return <div className="row"><span className="text-danger col-md-10">Il nag deve essere di almeno tre caratteri</span></div>
    }

    renderSpanFiliale = () => {
        return <div className="row"><span className="text-danger span-error-filiale col-md-2">Selezionare una filiale</span></div>
    }

    renderSpanNagAndFiliale = () => {
        return <div className="row"><span className="text-danger span-error-filiale col-md-2">Selezionare una filiale</span> 
        <span className="text-danger col-md-4 span-error-nag">Il nag deve essere di almeno tre caratteri</span></div>
    }

    render() { 
        const {filiali} = this.props;
        const {nagError, ricerca, clienti, cliente, showListaClienti, isConfermata, isNotConfermata, filialeError} = this.state;
        return ( 
            <div>
            {/* <Navbar username={this.props.username}/> */}
            <h2 className="text-left ricercaClienti">{LABELS.RICERCA_CLIENTE}</h2>
            <form className={!nagError ? "formRicercaClienti" : "formRicercaClientiError"}>
                <div className="row">
                    <label className="col-1 labelForm noPadding">{LABELS.FILIALE}</label>
                    <Select className={`col-md-2 ${filialeError === null ? "" : filialeError ? "reactselect-invalid" : "reactselect-valid"}`} placeholder="Seleziona Filiale" options={
                        filiali && filiali.length !== 0 && filiali.map(filiale => {
                            return {
                                value: filiale.id,
                                label: filiale.nome
                            }
                        })
                    }
                    onChange={filiale => this.onChangeFiliale(filiale)}
                    />
                    
                    <label className="col-1 labelForm noPadding">{LABELS.NAG}</label>
                    <input className={`col-md-2 form-control ${nagError ? "errorInput" : ""}`} placeholder="NAG NUMBER" name="nag" minLength={6} onChange={(e) => this.onChangeText(e)} value={ricerca.nag}/>

                    <label className="col-1 labelForm noPadding">{LABELS.NOME}</label>
                    <input className="col-md-2 form-control" placeholder="NOME CLIENTE" name="nome" onChange={(e) => this.onChangeText(e)} value={ricerca.nome}/>

                    <label className="col-md-1 labelForm noPadding">{LABELS.DATA_DI_NASCITA}</label>
                    <DatePicker
                        className="col-md-1"
                        onChange={this.onChange}
                        value={ricerca.date}
                    />
                </div>
                
                {/*here i'm going to see if there is nag error error, if not i'll check filiale error. If both are false then I'll show the simple  */}
                {/*page without errors. If both are true two errors will be shown  */}
                {nagError && !filialeError ? <React.Fragment>{this.renderSpanNag()}{this.renderButtonCerca()}</React.Fragment>
                : filialeError && !nagError ? <React.Fragment>{this.renderSpanFiliale()}{this.renderButtonCerca()}</React.Fragment>
                : filialeError && nagError ? <React.Fragment>{this.renderSpanNagAndFiliale()}{this.renderButtonCerca()}</React.Fragment>
                : <React.Fragment>{this.renderButtonCerca()}</React.Fragment>}
                <br /> 
            </form>

            {clienti.length !== 0 && showListaClienti ?
            <table className="table table-striped tableClienti">
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
             {clienti.map(cliente => {
                 return(<tr key={cliente.id}>
                    <td>{cliente.cab ? cliente.cab : ""}</td>
                    <td>{cliente.nag ? cliente.nag : ""}</td>
                    <td>{cliente.nome ? cliente.nome : ""}</td>
                    <td>{cliente.dataNascita ? <Moment format="DD/MM/YYYY">{cliente.dataNascita}</Moment> : ""}</td>
                    <td><button className="btn btn-light bottoneDettaglio" onClick={() => this.getCliente(cliente)}>{LABELS.DETTAGLIO}</button></td>
                    
                  </tr>
             )})} </tbody>
              </table>: ""} 

            {/* ANAGRAFICA CONFERMATA */}
            {isConfermata ? 
            <div className="text-left bottoneRicerca">
                <h2 className="col-md-2 offset-md-3">{LABELS.ATTENZIONE}</h2>
                <p className="col-md-3 offset-md-3">{LABELS.ANAGRAFICA_CLIENTE_TEXT}</p>
                <p className="col-md-2 offset-md-3">{LABELS.DATA_INSERITA} {" "} {<Moment format="DD/MM/YYYY">{cliente.lastModify}</Moment>}</p>
                <p className="col-md-2 offset-md-3">{LABELS.CODICE_UNIVOCO} {" "} {cliente.codice}</p>
                <button type="button" className="btn btn-primary col-md-1 offset-md-3" onClick={() => this.props.downloadFile()}>{LABELS.SCARICA}</button>
                <button className="btn btn-primary col-md-1 offset-1" onClick={() => this.tornaAllaLista()}>{LABELS.TORNA_ALLA_LISTA}</button>
                </div>
                : ""}

            {isNotConfermata ? <AnagraficaDaVerificare tornaAllaLista={this.tornaAllaLista} cliente={cliente} handleVerifyRegistry={this.props.handleVerifyRegistry}/> : ""}
            </div>
         );
    }
}
 