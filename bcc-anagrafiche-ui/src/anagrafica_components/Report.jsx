import React, { Component } from 'react';
import {REPORT_LABELS} from "./common/Constants";

class Report extends Component {
    state = {  
        handleTotali: {},
        statsTotali: {}
    }

    componentWillMount(){
        this.props.handleTotali();  
    }

    componentWillReceiveProps(props) {
        this.setState({statsTotali: props.statsTotali})
    }

    render() {
        const {statsTotali} = this.state
        return (
            <div>
                <h2 className="ricercaClienti text-left">Statistiche</h2>
                <fieldset className="commondFieldset text-left">
					{REPORT_LABELS.ANAGRAFICHE_CONFERMATE} <span className="font-weight-bold">{statsTotali ? statsTotali.totConfirmedRecords : ""}</span>
                    <br />
                    {REPORT_LABELS.ANAGRAFICHE_CON_MODIFICA} <span className="font-weight-bold">TDB</span>
                    <br />
                    {REPORT_LABELS.ANAGRAFICHE_NON_CONFERMATE} <span className="font-weight-bold">{statsTotali ? statsTotali.totNotConfirmedRecords : ""}</span>
                    <br />
                    {REPORT_LABELS.CLIENTI_CARICATI} <span className="font-weight-bold">{statsTotali ? statsTotali.totCustomers : ""}</span>
                </fieldset>

                <div className="commondFieldsetLabel font-weight-bold text-left">Dettaglio campi modificati</div>
                <fieldset className="commondFieldset text-left">
                    {REPORT_LABELS.MODIFICA_NUMERO_TELEFONO} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedPhone : ""}</span>
                    <br />
                    {REPORT_LABELS.MODIFICA_EMAIL} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedEmail : ""}</span>
                    <br />
                    {REPORT_LABELS.MODIFCA_PRIVACY}
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;{REPORT_LABELS.P1} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedPrivacy1 : ""}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;{REPORT_LABELS.P2} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedPrivacy2 : ""}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;{REPORT_LABELS.P3} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedPrivacy3 : ""}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;{REPORT_LABELS.P4} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedPrivacy4 : ""}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;{REPORT_LABELS.P5} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedPrivacy5 : ""}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;{REPORT_LABELS.P6} <span className="font-weight-bold">{statsTotali ? statsTotali.totEditedPrivacy6 : ""}</span>
                </fieldset>
            </div>
        );
    }
}
 
export default Report;