import React, { Component } from 'react';
import Navbar from './Navbar';

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
        return (
            <div>
                <h2 className="ricercaClienti text-left">Statistiche</h2>
                <fieldset className="commondFieldset text-left">
					Anagrafiche confermate: <span className="font-weight-bold">{this.state.statsTotali.totConfirmedRecords}</span>
                    <br />
                    Anagrafiche con modifica: <span className="font-weight-bold">TDB</span>
                    <br />
                    Anagrafiche non ancora confermate: <span className="font-weight-bold">{this.state.statsTotali.totNotConfirmedRecords}</span>
                    <br />
                    Totale clienti caricati: <span className="font-weight-bold">{this.state.statsTotali.totCustomers}</span>
                </fieldset>

                <div className="commondFieldsetLabel font-weight-bold text-left">Dettaglio campi modificati</div>
                <fieldset className="commondFieldset text-left">
                    Modifica numero di telefono: <span className="font-weight-bold">{this.state.statsTotali.totEditedPhone}</span>
                    <br />
                    Modifica email: <span className="font-weight-bold">{this.state.statsTotali.totEditedEmail}</span>
                    <br />
                    Modifica Privacy
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 1: <span className="font-weight-bold">{this.state.statsTotali.totEditedPrivacy1}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 2: <span className="font-weight-bold">{this.state.statsTotali.totEditedPrivacy2}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 3: <span className="font-weight-bold">{this.state.statsTotali.totEditedPrivacy3}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 4: <span className="font-weight-bold">{this.state.statsTotali.totEditedPrivacy4}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 5: <span className="font-weight-bold">{this.state.statsTotali.totEditedPrivacy5}</span>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 6: <span className="font-weight-bold">{this.state.statsTotali.totEditedPrivacy6}</span>
                </fieldset>
            </div>
        );
    }
}
 
export default Report;