import React, { Component } from 'react';
import Navbar from './Navbar';
import {LABELS} from "./common/Constants";

class OperazioneCompletata extends Component {
    state = {  }

    backToResearch = () => {
        this.props.history.push("/ricerca-clienti");
    }

    render() { 
        return ( 
        <React.Fragment>
        {/* <Navbar username={this.props.username}/>  */}
        <div className="containerOperation">
        <div className="row">
            <h3 className="col-md-12">{LABELS.OPERAZIONE_COMPLETATA}</h3>
            <p  className="col-md-12">{LABELS.CODICE_ASSEGNATO}</p>
            <p  className="col-md-12 mb-5">{this.props.codiceUnivoco ? this.props.codiceUnivoco : ""}</p>
            <button className="col-md-3 offset-md-2 btn btn-success" onClick={() => this.backToResearch()}>{LABELS.NUOVA_RICERCA}</button>
            <button className="col-md-3 offset-md-2 btn btn-primary" onClick={() => this.props.downloadFile()}>{LABELS.SCARICA}</button>
            
        </div>
        </div>
        </React.Fragment>
        );
    }
}
 
export default OperazioneCompletata;