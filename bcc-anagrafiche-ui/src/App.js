import React, { Component } from 'react';
import './App.css';
import { withRouter, Route, Switch } from "react-router-dom";
import {Login} from "./anagrafica_components/Login";
import {USER_TYPE} from "./anagrafica_components/common/Constants";
import {RicercaClienti} from "./anagrafica_components/RicercaClienti";
import {ImportaClienti} from "./anagrafica_components/ImportaClienti";
import OperazioneCompletata from './anagrafica_components/OperazioneCompletata';
import axios from "axios";
import config from "./config.json";
import dotenv from "dotenv";
import Report from './anagrafica_components/Report';
import Navbar from './anagrafica_components/Navbar';

dotenv.config();

class App extends Component {
  state = { 
    userType: null,
    username: "",
    roles: [],
    filiali: [],
    clienti: [],
    codiceUnivoco: null
   }

   componentWillMount() {
     if(this.props.location === "" || this.props.location === "/")
    this.props.history.push("/login");

    for (let api in config) {
      config[api] = config[api].replace(
        "[REACT_APP_URL_JAVA]",
        process.env.REACT_APP_URL_JAVA
      );
    }
    // this.handleCheckToken(this.props.location.pathname);
   }

  handleLogin = (loginRequest) => {
    const headers = { "Content-Type": "application/json"};
    const conf = { headers: { ...headers } };

    let roles = [];

    axios.post(config.apiLoginEndpoint, loginRequest, conf)
    .then(response => {console.log(response)
        roles = [...response.data.roles];
        localStorage.setItem("TOKEN", response.data.accessToken);
        localStorage.setItem("USERNAME", response.data.username);
        this.setState({roles: roles, username: response.data.username,
           userType: roles.length === 1 && roles[0].authority === USER_TYPE.USER ? USER_TYPE.USER : USER_TYPE.ADMINISTRATOR})
        console.log(roles[0].authority, roles[0])
        if(roles.length === 1 && roles[0].authority === USER_TYPE.USER){
          this.utilitiesForUser();
          this.props.history.push("/ricerca-clienti");
        } else {
          this.props.history.push("/importa-clienti");
        }
     }
    )
    .catch(err => console.log(err));
  }

  utilitiesForUser = () => {
    this.handleFindFiliali();
  }

  handleFindFiliali = () => {
    
    const headers = { "Authorization": localStorage.getItem("TOKEN")};
    const conf = { headers: { ...headers } };

    axios.get(config.apiFilialiEndpoint, conf)
    .then(response => {this.setState({filiali: response.data})})
    .catch(err => console.log(err))
  }

  handleFindCliente = (values) => {
    
    const headers = { "Authorization": localStorage.getItem("TOKEN")};
    const conf = { headers: { ...headers } };
    let params = {};
    if(values.nome !== "" && values.date !== null)
    params = {params: {"idFiliale": values.filiale, "nag": values.nag, "nome": values.nome, "dataNascita": values.date}}
    else if(values.nome !== "" && values.date === null)
    params = {params: {"idFiliale": values.filiale, "nag": values.nag, "nome": values.nome}}
    else if(values.nome === "" && values.date === null)
    params = {params: {"idFiliale": values.filiale, "nag": values.nag}}

    axios.get(config.apiClienteEndpoint, conf)
    .then(response => this.setState({clienti: response.data}))
    .catch(err => console.log(err))
  }

  handleVerifyRegistry = (markAsEditedRequest, codiceUnivoco) => {
    const headers = { "Content-Type": "application/json", "Authorization": localStorage.getItem("TOKEN")};
    const conf = { headers: { ...headers } };
    console.log(markAsEditedRequest)
    axios.post(config.apiVerifyAnagraficaEndpoint, markAsEditedRequest, conf)
    .then(response => {
      if(response.data === "OK"){
        this.setState({codiceUnivoco: codiceUnivoco, clienti: []});
        this.props.history.push("/ricerca-completata");
      }
    })
    .catch(err => console.log(err))
  }

  goToReport = () => {
    this.props.history.push("/report");
  }

  goToResearch = () => {
    this.props.history.push("/ricerca-clienti");
  }

  render() { 
    return (
      <div className="App">
        <Navbar goToReport={this.goToReport} goToResearch={this.goToResearch}/>
         <Switch>
              <Route
                path="/login"
                exact
                render={(props) => <Login {...props} handleLogin={this.handleLogin}/>}
              />
              <Route
                path="/"
                exact
                render={(props) => <Login {...props} handleLogin={this.handleLogin}/>}
              />
              <Route
                path="/ricerca-clienti"
                exact
                render={(props) => <RicercaClienti {...props} handleFindCliente={this.handleFindCliente} 
                                                  username={this.state.username} userType={this.state.userType}
                                                  filiali={this.state.filiali} handleFindFiliali={this.handleFindFiliali}
                                                  clienti={this.state.clienti} handleVerifyRegistry={this.handleVerifyRegistry}/>}
              />
               <Route
                path="/importa-clienti"
                exact
                render={(props) => <ImportaClienti  {...props}/>}
              />
              <Route
                path="/ricerca-completata"
                exact
                render={(props) => <OperazioneCompletata  {...props} codiceUnivoco={this.state.codiceUnivoco} username={this.state.username}/>}
              />
              <Route
                path="/report"
                exact
                render={(props) => <Report  {...props} />}
              />
          </Switch>
      </div>
    );
  }
}
 
export default withRouter(App);
