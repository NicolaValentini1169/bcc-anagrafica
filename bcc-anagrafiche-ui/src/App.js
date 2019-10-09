import React, { Component } from 'react';
import './App.css';
import { withRouter, Route, Switch/* , Redirect */ } from "react-router-dom";
import {Login} from "./anagrafica_components/Login";
import {USER_TYPE} from "./anagrafica_components/common/Constants";
import {RicercaClienti} from "./anagrafica_components/RicercaClienti";
import {ImportaClienti} from "./anagrafica_components/ImportaClienti";
import OperazioneCompletata from './anagrafica_components/OperazioneCompletata';
import axios from "axios";
import config from "./config.json";
import dotenv from "dotenv";
import Report from './anagrafica_components/Report';
import Navbar from './anagrafica_components/common/Navbar';

dotenv.config();

class App extends Component {
  state = { 
    userType: null,
    username: "",
    roles: [],
    filiali: [],
    clienti: [],
    codiceUnivoco: null,
    statsTotali: {}
   }

   componentWillMount() {
    //this if handle an eventual modification of URL from the user and redirect it to the login
    if(this.props.location.pathname === "/" || this.props.location.pathname === "" || this.props.location.pathname === window.defConfigurations.url_prefix)
    this.props.history.replace(window.defConfigurations.url_prefix + "login");
    
    for (let api in config) {
      config[api] = config[api].replace(
        "[REACT_APP_URL_JAVA]",
        window.defConfigurations.REACT_APP_URL_JAVA
      );
    }
    
   }

  handleLogin = (loginRequest) => {
    const headers = { "Content-Type": "application/json"};
    const conf = { headers: { ...headers } };

    let roles = [];

    axios.post(config.apiLoginEndpoint, loginRequest, conf)
    .then(response => {
        roles = [...response.data.roles];
        //saving token and username in local storage to persist data for the session
        localStorage.setItem("TOKEN", response.data.accessToken);
        localStorage.setItem("USERNAME", response.data.username);

        this.setState({roles: roles, username: response.data.username,
          userType: roles.length === 1 && roles[0].authority === USER_TYPE.USER ? USER_TYPE.USER : USER_TYPE.ADMINISTRATOR})
        //checking if the user logged is a simple user or an admin
        if(roles.length === 1 && roles[0].authority === USER_TYPE.USER){
          this.utilitiesForUser();
          this.props.history.replace(window.defConfigurations.url_prefix + "ricerca-clienti");
        } else {
          this.props.history.replace(window.defConfigurations.url_prefix + "importa-clienti");
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
    //this if are needed to check if the user compiled all field or only the required fields. Then create the axios get and handle the response
    if(values.nome !== "" && values.date !== null){
      axios.get(config.apiClienteEndpoint + "?branch=" + values.filiale + "&nag=" + values.nag + "&customerName=" + values.nome + "&birthDate=" + values.date , conf)
      .then(response => this.setState({clienti: response.data}))
      .catch(err => console.log(err.response))
    }
    else if(values.nome === "" && values.date !== null){
      axios.get(config.apiClienteEndpoint + "?branch=" + values.filiale + "&nag=" + values.nag + "&birthDate=" + values.date , conf)
      .then(response => this.setState({clienti: response.data}))
      .catch(err => console.log(err.response))
    }
    else if(values.nome !== "" && values.date === null){
      axios.get(config.apiClienteEndpoint + "?branch=" + values.filiale + "&nag=" + values.nag + "&customerName=" + values.nome , conf)
      .then(response => this.setState({clienti: response.data}))
      .catch(err => console.log(err.response))
    }
    else if(values.nome === "" && values.date === null){
      axios.get(config.apiClienteEndpoint + "?branch=" + values.filiale + "&nag=" + values.nag, conf)
      .then(response => this.setState({clienti: response.data}))
      .catch(err => console.log(err.response))
    }
  }


  handleTotali = () => {
    const headers = { "Authorization": localStorage.getItem("TOKEN")};
    const conf = { headers: { ...headers } };
    axios.get(config.apiStatsTotali, conf)
      .then(response => {this.setState({statsTotali: response.data})})
      .catch(err => console.log(err.response))
  }


  handleVerifyRegistry = (markAsEditedRequest, codiceUnivoco) => {
    const headers = { "Content-Type": "application/json", "Authorization": localStorage.getItem("TOKEN")};
    const conf = { headers: { ...headers } };
    axios.post(config.apiVerifyAnagraficaEndpoint, markAsEditedRequest, conf)
    .then(response => {
      if(response.data === "OK"){
        this.setState({codiceUnivoco: codiceUnivoco, clienti: []});
        this.props.history.push(window.defConfigurations.url_prefix + "ricerca-completata");
      }
    })
    .catch(err => console.log(err))
  }

  downloadFile = () => {
    const headers = { "Content-Type": "application/json", "Authorization": localStorage.getItem("TOKEN")};
    const conf = { headers: { ...headers } };

    axios
      .get(config.apiDownloadEndpoint, conf)
      .then(({ data: downloadedFile }) => {
        let byteArray = new Uint8Array(downloadedFile.file);
        const url = window.URL.createObjectURL(new Blob([byteArray]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", downloadedFile.name);
        document.body.appendChild(link);
        link.click();
      })
      .catch(error => console.log(error));
  };

  renderNavbar() {
    //if the page is login navbar must not be shown
    return this.props.location.pathname === "/" || this.props.location.pathname === "/login"
      || this.props.location.pathname === window.defConfigurations.url_prefix + "login"
      || this.props.location.pathname === "" ? "" : <Navbar />;
  }

  render() { 
    return (
      <div className="App">
        {this.renderNavbar()}
         <Switch>
              <Route
                path={window.defConfigurations.url_prefix + "login"}
                exact
                render={(props) => <Login {...props} handleLogin={this.handleLogin}/>}
              />
              <Route
                path={window.defConfigurations.url_prefix + "ricerca-clienti"}
                exact
                render={(props) => <RicercaClienti {...props} handleFindCliente={this.handleFindCliente} 
                                                  username={this.state.username} userType={this.state.userType}
                                                  filiali={this.state.filiali} handleFindFiliali={this.handleFindFiliali}
                                                  clienti={this.state.clienti} handleVerifyRegistry={this.handleVerifyRegistry}
                                                  downloadFile={this.downloadFile}/>}
              />
               <Route
                path={window.defConfigurations.url_prefix + "importa-clienti"}
                exact
                render={(props) => <ImportaClienti  {...props}/>}
              />
              <Route
                path={window.defConfigurations.url_prefix + "ricerca-completata"}
                exact
                render={(props) => <OperazioneCompletata  {...props} codiceUnivoco={this.state.codiceUnivoco} username={this.state.username} downloadFile={this.downloadFile}/>}
              />
              <Route
                path={window.defConfigurations.url_prefix + "report"}
                exact
                render={(props) => <Report  {...props} handleTotali={this.handleTotali} statsTotali={this.state.statsTotali}/>}
              />
              {/* <Redirect from="/" to={this.state.userType === USER_TYPE.USER && this.state.username !== "" ? window.defConfigurations.url_prefix + "ricerca-clienti" : this.state.username !== "" ? window.defConfigurations.url_prefix + "importa-clienti" : window.defConfigurations.url_prefix + "login"} /> */}
          </Switch>
      </div>
    );
  }
}
 
export default withRouter(App);
