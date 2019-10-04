import React, { Component } from 'react';
import {LABELS} from "./common/Constants";

class Navbar extends Component {
    state = {  }

    render() { 
        return (
     <nav className="navbar navbar-light bg-light">
         <div className="row col-md-12">
        <span className="navbar-brand mb-0 h1 col-md-2 text-left">{this.props.username ? this.props.username : localStorage.getItem("USERNAME")}</span>
        <button className="btn btn-link col-md-1" onClick={() => this.props.goToResearch()}>Ricerca Cliente</button>
        <button className="btn btn-link col-md-1 ml-2" onClick={() => this.props.goToReport()}>Report</button>
        <button className="btn btn-danger col-md-1 offset-md-6">{LABELS.LOGOUT}</button>
        </div>
    </nav> );
    }
}
 
export default Navbar;