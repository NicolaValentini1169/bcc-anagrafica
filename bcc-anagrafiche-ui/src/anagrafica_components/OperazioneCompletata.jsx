import React, { Component } from 'react';
import {LABELS} from "./common/Constants";
import { Link } from "react-router-dom";

class OperazioneCompletata extends Component {
    state = {  }


    render() { 
        return ( 
        <React.Fragment>
        {/* <Navbar username={this.props.username}/>  */}
        <div className="containerOperation">
        <div className="row">
            <h3 className="col-md-12">{LABELS.OPERAZIONE_COMPLETATA}</h3>
            <p  className="col-md-12">{LABELS.CODICE_ASSEGNATO}</p>
            <p  className="col-md-12 mb-5">{this.props.codiceUnivoco ? this.props.codiceUnivoco : ""}</p>
            <Link className="col-md-3 offset-md-2 btn btn-success" to="/ricerca-clienti">{LABELS.NUOVA_RICERCA}</Link>
            <button className="col-md-3 offset-md-2 btn btn-primary" onClick={() => this.props.downloadFile()}>{LABELS.SCARICA}</button>
            
        </div>
        </div>
        </React.Fragment>
        );
    }
}
 
export default OperazioneCompletata;