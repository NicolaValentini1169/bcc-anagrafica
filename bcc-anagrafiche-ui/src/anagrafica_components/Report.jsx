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
                Anagrafiche confermate: {this.state.statsTotali.totConfirmedRecords}
                    <br />
                    Anagrafiche con modifica: TDB
                    <br />
                    Anagrafiche non ancora confermate: {this.state.statsTotali.totNotConfirmedRecords}
                    <br />
                    Totale clienti caricati: {this.state.statsTotali.totCustomers}
                </fieldset>

                <div className="commondFieldsetLabel text-left font-weight-bold">Dettaglio campi modificati</div>
                <fieldset className="commondFieldset text-left">
                    Modifica numero di telefono: {this.state.statsTotali.totEditedPhone}
                    <br />
                    Modifica email: {this.state.statsTotali.totEditedEmail}
                    <br />
                    Modifica Privacy
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 1: {this.state.statsTotali.totEditedPrivacy1}
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 2: {this.state.statsTotali.totEditedPrivacy2}
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 3: {this.state.statsTotali.totEditedPrivacy3}
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 4: {this.state.statsTotali.totEditedPrivacy4}
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 5: {this.state.statsTotali.totEditedPrivacy5}
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;Privacy 6: {this.state.statsTotali.totEditedPrivacy6}
                </fieldset>
            </div>
        );
    }
}
 
export default Report;